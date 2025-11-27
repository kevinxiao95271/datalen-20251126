package com.datalen.config;

import com.datalen.dataset.entity.Dataset;
import com.datalen.dataset.repository.DatasetRepository;
import com.datalen.metric.entity.Metric;
import com.datalen.metric.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化类
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化指标
        initializeMetrics();

        // 初始化数据集
        initializeDatasets();
    }

    /**
     * 初始化指标
     */
    private void initializeMetrics() {
        // 检查是否已存在指标
        if (metricRepository.count() == 0) {
            // 创建达成率指标
            Metric achieveRateMetric = new Metric();
            achieveRateMetric.setCode("achieve_rate");
            achieveRateMetric.setName("达成率");
            achieveRateMetric.setExpression("SUM(sales)/NULLIF(SUM(target),0)");
            achieveRateMetric.setDescription("销售额与目标额的比率");
            metricRepository.save(achieveRateMetric);

            // 创建平均销售额指标
            Metric avgSalesMetric = new Metric();
            avgSalesMetric.setCode("avg_sales");
            avgSalesMetric.setName("平均销售额");
            avgSalesMetric.setExpression("AVG(sales)");
            avgSalesMetric.setDescription("平均销售额");
            metricRepository.save(avgSalesMetric);

            // 创建平均目标额指标
            Metric avgTargetMetric = new Metric();
            avgTargetMetric.setCode("avg_target");
            avgTargetMetric.setName("平均目标额");
            avgTargetMetric.setExpression("AVG(target)");
            avgTargetMetric.setDescription("平均目标额");
            metricRepository.save(avgTargetMetric);
        }
    }

    /**
     * 初始化数据集
     */
    private void initializeDatasets() {
        // 检查是否已存在数据集
        if (datasetRepository.count() == 0) {
            // 创建销售汇总数据集
            Dataset dataset = new Dataset();
            dataset.setId("ds_sales_summary");
            dataset.setName("销售汇总数据集");
            dataset.setModelId("model_sales");
            dataset.setSqlText("SELECT * FROM t_sales s JOIN t_dept d ON s.dept_id = d.id");
            datasetRepository.save(dataset);
        }
    }
}