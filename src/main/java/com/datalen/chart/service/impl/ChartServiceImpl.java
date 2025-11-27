package com.datalen.chart.service.impl;

import com.datalen.chart.dto.ChartRequestDTO;
import com.datalen.chart.service.ChartService;
import com.datalen.dataset.entity.Dataset;
import com.datalen.dataset.repository.DatasetRepository;
import com.datalen.metric.entity.Metric;
import com.datalen.metric.service.MetricService;
import com.datalen.model.entity.Model;
import com.datalen.model.entity.ModelField;
import com.datalen.model.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 图表服务实现类
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private ModelService modelService;

    @Autowired
    private MetricService metricService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 生成图表数据
     */
    @Override
    public Map<String, Object> generateChartData(ChartRequestDTO chartRequest) {
        // 1. 获取数据集
        String datasetId = chartRequest.getDatasetId();
        Optional<Dataset> optionalDataset = datasetRepository.findById(datasetId);
        if (!optionalDataset.isPresent()) {
            throw new RuntimeException("Dataset not found: " + datasetId);
        }
        Dataset dataset = optionalDataset.get();

        // 2. 获取模型
        String modelId = dataset.getModelId();
        Model model = modelService.getModelById(modelId);
        if (model == null) {
            throw new RuntimeException("Model not found: " + modelId);
        }

        // 3. 构建SQL查询
        String sql = buildSql(chartRequest, model, dataset);

        // 4. 执行查询
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

        // 5. 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("sql", sql);
        result.put("data", resultList);
        result.put("dimensions", chartRequest.getDimensions());
        result.put("metrics", chartRequest.getMetrics());
        result.put("chartType", chartRequest.getChartType());

        return result;
    }

    /**
     * 构建SQL查询
     */
    private String buildSql(ChartRequestDTO chartRequest, Model model, Dataset dataset) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<String> selectClauses = new ArrayList<>();
        List<String> groupClauses = new ArrayList<>();

        // 处理维度字段
        for (String dimension : chartRequest.getDimensions()) {
            // 查找模型中的字段
            Optional<ModelField> optionalField = model.getFields().stream()
                    .filter(f -> f.getCode().equals(dimension))
                    .findFirst();

            if (optionalField.isPresent()) {
                ModelField field = optionalField.get();
                String selectClause = field.getTableAlias() + "." + field.getColumnName() + " AS " + dimension;
                selectClauses.add(selectClause);
                groupClauses.add(field.getTableAlias() + "." + field.getColumnName());
            }
        }

        // 处理指标字段
        for (ChartRequestDTO.MetricRequestDTO metricRequest : chartRequest.getMetrics()) {
            String metricCode = metricRequest.getCode();
            String metricAlias = metricRequest.getAlias();

            // 查找指标
            Metric metric = metricService.getMetricByCode(metricCode);
            if (metric != null) {
                // 使用自定义指标表达式
                String expression = metric.getExpression();
                String selectClause = expression + " AS " + metricCode;
                selectClauses.add(selectClause);
            } else {
                // 查找模型中的字段
                Optional<ModelField> optionalField = model.getFields().stream()
                        .filter(f -> f.getCode().equals(metricCode))
                        .findFirst();

                if (optionalField.isPresent()) {
                    ModelField field = optionalField.get();
                    String selectClause = field.getAggFunction() + "(" + field.getTableAlias() + "." + field.getColumnName() + ") AS " + metricCode;
                    selectClauses.add(selectClause);
                }
            }
        }

        // SELECT 子句
        sqlBuilder.append("SELECT ");
        sqlBuilder.append(String.join(", ", selectClauses));

        // FROM 子句
        sqlBuilder.append(" FROM " + model.getBaseTable() + " s");

        // JOIN 子句
        for (com.datalen.model.entity.Join join : model.getJoins()) {
            sqlBuilder.append(" " + join.getJoinType() + " JOIN " + join.getJoinTable() + " " + join.getJoinAlias());
            sqlBuilder.append(" ON " + join.getLeftColumn() + " = " + join.getRightColumn());
        }

        // GROUP BY 子句
        if (!groupClauses.isEmpty()) {
            sqlBuilder.append(" GROUP BY " + String.join(", ", groupClauses));
        }

        // ORDER BY 子句
        if (!groupClauses.isEmpty()) {
            sqlBuilder.append(" ORDER BY " + String.join(", ", groupClauses));
        }

        return sqlBuilder.toString();
    }
}