package com.datalen.model.service.impl;

import com.datalen.model.entity.Join;
import com.datalen.model.entity.Model;
import com.datalen.model.entity.ModelField;
import com.datalen.model.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 模型服务实现类
 */
@Service
public class ModelServiceImpl implements ModelService {

    // 模拟数据，实际项目中从数据库获取
    private final List<Model> models = new ArrayList<>();

    /**
     * 初始化模拟数据
     */
    public ModelServiceImpl() {
        // 创建销售分析模型
        Model salesModel = new Model();
        salesModel.setId("model_sales");
        salesModel.setName("销售分析模型");
        salesModel.setDescription("用于销售数据分析的模型");
        salesModel.setBaseTable("t_sales");

        // 创建模型字段
        List<ModelField> salesFields = new ArrayList<>();

        ModelField deptNameField = new ModelField();
        deptNameField.setCode("dept_name");
        deptNameField.setName("部门名称");
        deptNameField.setType("string");
        deptNameField.setTableAlias("d");
        deptNameField.setColumnName("dept_name");
        deptNameField.setIsDimension(true);
        deptNameField.setIsMeasure(false);
        deptNameField.setModel(salesModel);
        salesFields.add(deptNameField);

        ModelField regionNameField = new ModelField();
        regionNameField.setCode("region");
        regionNameField.setName("区域名称");
        regionNameField.setType("string");
        regionNameField.setTableAlias("r");
        regionNameField.setColumnName("region_name");
        regionNameField.setIsDimension(true);
        regionNameField.setIsMeasure(false);
        regionNameField.setModel(salesModel);
        salesFields.add(regionNameField);

        ModelField monthField = new ModelField();
        monthField.setCode("month");
        monthField.setName("月份");
        monthField.setType("string");
        monthField.setTableAlias("s");
        monthField.setColumnName("month");
        monthField.setIsDimension(true);
        monthField.setIsMeasure(false);
        monthField.setModel(salesModel);
        salesFields.add(monthField);

        ModelField totalSalesField = new ModelField();
        totalSalesField.setCode("total_sales");
        totalSalesField.setName("销售额");
        totalSalesField.setType("number");
        totalSalesField.setTableAlias("s");
        totalSalesField.setColumnName("sales");
        totalSalesField.setAggFunction("SUM");
        totalSalesField.setIsDimension(false);
        totalSalesField.setIsMeasure(true);
        totalSalesField.setModel(salesModel);
        salesFields.add(totalSalesField);

        ModelField targetSumField = new ModelField();
        targetSumField.setCode("target_sum");
        targetSumField.setName("目标额");
        targetSumField.setType("number");
        targetSumField.setTableAlias("s");
        targetSumField.setColumnName("target");
        targetSumField.setAggFunction("SUM");
        targetSumField.setIsDimension(false);
        targetSumField.setIsMeasure(true);
        targetSumField.setModel(salesModel);
        salesFields.add(targetSumField);

        ModelField achieveRateField = new ModelField();
        achieveRateField.setCode("achieve_rate");
        achieveRateField.setName("达成率");
        achieveRateField.setType("number");
        achieveRateField.setTableAlias("s");
        achieveRateField.setColumnName("sales/target");
        achieveRateField.setAggFunction("AVG");
        achieveRateField.setIsDimension(false);
        achieveRateField.setIsMeasure(true);
        achieveRateField.setModel(salesModel);
        salesFields.add(achieveRateField);

        salesModel.setFields(salesFields);

        // 创建模型连接
        List<Join> salesJoins = new ArrayList<>();

        Join deptJoin = new Join();
        deptJoin.setJoinTable("t_dept");
        deptJoin.setJoinAlias("d");
        deptJoin.setJoinType("LEFT");
        deptJoin.setLeftColumn("s.dept_id");
        deptJoin.setRightColumn("d.id");
        deptJoin.setModel(salesModel);
        salesJoins.add(deptJoin);

        Join regionJoin = new Join();
        regionJoin.setJoinTable("t_region");
        regionJoin.setJoinAlias("r");
        regionJoin.setJoinType("LEFT");
        regionJoin.setLeftColumn("d.region_id");
        regionJoin.setRightColumn("r.id");
        regionJoin.setModel(salesModel);
        salesJoins.add(regionJoin);

        salesModel.setJoins(salesJoins);

        // 添加到模型列表
        models.add(salesModel);
    }

    /**
     * 获取所有模型
     */
    @Override
    public List<Model> getAllModels() {
        return models;
    }

    /**
     * 根据ID获取模型
     */
    @Override
    public Model getModelById(String id) {
        Optional<Model> optionalModel = models.stream().filter(model -> model.getId().equals(id)).findFirst();
        return optionalModel.orElse(null);
    }
}