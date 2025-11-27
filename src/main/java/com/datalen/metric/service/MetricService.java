package com.datalen.metric.service;

import com.datalen.metric.entity.Metric;

import java.util.List;

/**
 * 指标服务接口
 */
public interface MetricService {
    /**
     * 获取所有指标
     */
    List<Metric> getAllMetrics();

    /**
     * 根据代码获取指标
     */
    Metric getMetricByCode(String code);

    /**
     * 创建指标
     */
    Metric createMetric(Metric metric);

    /**
     * 更新指标
     */
    Metric updateMetric(String code, Metric metric);

    /**
     * 删除指标
     */
    void deleteMetric(String code);
}