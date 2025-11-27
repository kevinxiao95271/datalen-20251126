package com.datalen.dataset.service.impl;

import com.datalen.dataset.dto.DatasetCreateRequestDTO;
import com.datalen.dataset.dto.DatasetQueryRequestDTO;
import com.datalen.dataset.dto.DatasetQueryResponseDTO;
import com.datalen.dataset.entity.Dataset;
import com.datalen.dataset.entity.DatasetField;
import com.datalen.dataset.entity.DatasetFilter;
import com.datalen.dataset.repository.DatasetRepository;
import com.datalen.dataset.service.DatasetService;
import com.datalen.model.entity.Model;
import com.datalen.model.entity.ModelField;
import com.datalen.model.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 数据集服务实现类
 */
@Service
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    private ModelService modelService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DatasetRepository datasetRepository;

    /**
     * 创建数据集
     */
    @Override
    public String createDataset(DatasetCreateRequestDTO request) {
        String datasetId = request.getId();
        
        // 创建Dataset实体
        Dataset dataset = new Dataset();
        dataset.setId(datasetId);
        dataset.setName(request.getName());
        dataset.setModelId(request.getModelId());
        dataset.setSqlText(request.getSqlText());
        dataset.setGroups(request.getGroups());
        
        // 处理字段
        List<DatasetField> datasetFields = new ArrayList<>();
        if (request.getFields() != null) {
            for (DatasetCreateRequestDTO.DatasetFieldDTO fieldDto : request.getFields()) {
                DatasetField datasetField = new DatasetField();
                datasetField.setDataset(dataset);
                datasetField.setCode(fieldDto.getCode());
                datasetField.setAlias(fieldDto.getAlias());
                datasetField.setAggFunction(fieldDto.getAggFunction());
                datasetFields.add(datasetField);
            }
        }
        dataset.setFields(datasetFields);
        
        // 处理过滤条件
        List<DatasetFilter> datasetFilters = new ArrayList<>();
        if (request.getFilters() != null) {
            for (DatasetCreateRequestDTO.DatasetFilterDTO filterDto : request.getFilters()) {
                DatasetFilter datasetFilter = new DatasetFilter();
                datasetFilter.setDataset(dataset);
                datasetFilter.setFieldCode(filterDto.getFieldCode());
                datasetFilter.setOperator(filterDto.getOperator());
                datasetFilter.setFilterValue(filterDto.getFilterValue());
                datasetFilters.add(datasetFilter);
            }
        }
        dataset.setFilters(datasetFilters);
        
        // 保存到数据库
        datasetRepository.save(dataset);
        return datasetId;
    }

    /**
     * 更新数据集
     */
    @Override
    public void updateDataset(String id, DatasetCreateRequestDTO request) {
        // 查找数据集
        Optional<Dataset> optionalDataset = datasetRepository.findById(id);
        if (optionalDataset.isPresent()) {
            Dataset dataset = optionalDataset.get();
            // 更新字段
            dataset.setName(request.getName());
            dataset.setModelId(request.getModelId());
            dataset.setSqlText(request.getSqlText());
            dataset.setGroups(request.getGroups());
            
            // 处理字段
            List<DatasetField> datasetFields = new ArrayList<>();
            if (request.getFields() != null) {
                for (DatasetCreateRequestDTO.DatasetFieldDTO fieldDto : request.getFields()) {
                    DatasetField datasetField = new DatasetField();
                    datasetField.setDataset(dataset);
                    datasetField.setCode(fieldDto.getCode());
                    datasetField.setAlias(fieldDto.getAlias());
                    datasetField.setAggFunction(fieldDto.getAggFunction());
                    datasetFields.add(datasetField);
                }
            }
            dataset.setFields(datasetFields);
            
            // 处理过滤条件
            List<DatasetFilter> datasetFilters = new ArrayList<>();
            if (request.getFilters() != null) {
                for (DatasetCreateRequestDTO.DatasetFilterDTO filterDto : request.getFilters()) {
                    DatasetFilter datasetFilter = new DatasetFilter();
                    datasetFilter.setDataset(dataset);
                    datasetFilter.setFieldCode(filterDto.getFieldCode());
                    datasetFilter.setOperator(filterDto.getOperator());
                    datasetFilter.setFilterValue(filterDto.getFilterValue());
                    datasetFilters.add(datasetFilter);
                }
            }
            dataset.setFilters(datasetFilters);
            
            // 保存更新
            datasetRepository.save(dataset);
        } else {
            throw new RuntimeException("Dataset not found: " + id);
        }
    }

    /**
     * 删除数据集
     */
    @Override
    public void deleteDataset(String id) {
        datasetRepository.deleteById(id);
    }

    /**
     * 查询数据集
     */
    @Override
    public DatasetQueryResponseDTO queryDataset(String id, DatasetQueryRequestDTO request) {
        // 查找数据集
        Optional<Dataset> optionalDataset = datasetRepository.findById(id);
        if (!optionalDataset.isPresent()) {
            throw new RuntimeException("Dataset not found: " + id);
        }
        
        Dataset dataset = optionalDataset.get();
        
        // 如果有保存的SQL，则直接使用
        String sql;
        if (dataset.getSqlText() != null && !dataset.getSqlText().isEmpty()) {
            sql = dataset.getSqlText();
            // 添加分页
            sql += " LIMIT " + request.getLimit() + " OFFSET " + request.getOffset();
        } else {
            // 获取模型信息
            Model model = modelService.getModelById(dataset.getModelId());
            if (model == null) {
                throw new RuntimeException("Model not found: " + dataset.getModelId());
            }
            
            // 创建临时请求对象用于生成SQL
            DatasetCreateRequestDTO datasetRequest = new DatasetCreateRequestDTO();
            datasetRequest.setId(dataset.getId());
            datasetRequest.setName(dataset.getName());
            datasetRequest.setModelId(dataset.getModelId());
            
            // 生成SQL
            sql = generateSql(datasetRequest, model, request);
        }
        
        System.out.println("Generated SQL: " + sql);

        // 执行SQL并返回结果
        return executeSql(sql, request);
    }

    /**
     * 生成SQL
     */
    private String generateSql(DatasetCreateRequestDTO datasetRequest, Model model, DatasetQueryRequestDTO queryRequest) {
        StringBuilder sqlBuilder = new StringBuilder();

        // SELECT 子句
        sqlBuilder.append("SELECT ");
        List<String> selectColumns = new ArrayList<>();

        for (DatasetCreateRequestDTO.DatasetFieldDTO fieldDto : datasetRequest.getFields()) {
            // 查找模型中的字段
            Optional<ModelField> optionalField = model.getFields().stream()
                    .filter(f -> f.getCode().equals(fieldDto.getCode()))
                    .findFirst();

            if (optionalField.isPresent()) {
                ModelField field = optionalField.get();
                String columnExpr;
                
                // 如果是度量字段且有聚合函数，则添加聚合
                if (field.getIsMeasure() && field.getAggFunction() != null) {
                    columnExpr = field.getAggFunction() + "(" + field.getTableAlias() + "." + field.getColumnName() + ") AS " + field.getCode();
                } else {
                    // 维度字段直接使用
                    columnExpr = field.getTableAlias() + "." + field.getColumnName() + " AS " + field.getCode();
                }
                selectColumns.add(columnExpr);
            }
        }

        sqlBuilder.append(String.join(", ", selectColumns));

        // FROM 子句
        sqlBuilder.append(" FROM " + model.getBaseTable() + " s");

        // JOIN 子句
        for (com.datalen.model.entity.Join join : model.getJoins()) {
            sqlBuilder.append(" " + join.getJoinType() + " JOIN " + join.getJoinTable() + " " + join.getJoinAlias());
            sqlBuilder.append(" ON " + join.getLeftColumn() + " = " + join.getRightColumn());
        }

        // GROUP BY 子句
        List<String> groupColumns = new ArrayList<>();
        for (String groupField : datasetRequest.getGroups()) {
            // 查找模型中的字段
            Optional<ModelField> optionalField = model.getFields().stream()
                    .filter(f -> f.getCode().equals(groupField))
                    .findFirst();

            if (optionalField.isPresent()) {
                ModelField field = optionalField.get();
                groupColumns.add(field.getTableAlias() + "." + field.getColumnName());
            }
        }

        if (!groupColumns.isEmpty()) {
            sqlBuilder.append(" GROUP BY " + String.join(", ", groupColumns));
        }

        // ORDER BY 子句（默认按第一个分组字段排序）
        if (!groupColumns.isEmpty()) {
            sqlBuilder.append(" ORDER BY " + groupColumns.get(0));
        }

        // 分页
        sqlBuilder.append(" LIMIT " + queryRequest.getLimit() + " OFFSET " + queryRequest.getOffset());

        return sqlBuilder.toString();
    }

    /**
     * 执行SQL并返回结果
     */
    private DatasetQueryResponseDTO executeSql(String sql, DatasetQueryRequestDTO request) {
        DatasetQueryResponseDTO response = new DatasetQueryResponseDTO();

        // 执行查询
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        if (rows.isEmpty()) {
            response.setColumns(new ArrayList<>());
            response.setRows(new ArrayList<>());
            response.setTotal(0);
            return response;
        }

        // 获取列名
        List<String> columns = new ArrayList<>(rows.get(0).keySet());
        response.setColumns(columns);

        // 转换行数据
        List<List<Object>> resultRows = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            List<Object> resultRow = new ArrayList<>();
            for (String column : columns) {
                resultRow.add(row.get(column));
            }
            resultRows.add(resultRow);
        }
        response.setRows(resultRows);

        // 计算总数（实际项目中需要单独查询count）
        response.setTotal(rows.size());

        return response;
    }
}