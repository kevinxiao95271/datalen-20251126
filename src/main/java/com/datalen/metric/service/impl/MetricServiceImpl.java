package com.datalen.metric.service.impl;

import com.datalen.metric.entity.Metric;
import com.datalen.metric.repository.MetricRepository;
import com.datalen.metric.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 指标服务实现类
 */
@Service
public class MetricServiceImpl implements MetricService {

    @Autowired
    private MetricRepository metricRepository;

    /**
     * 获取所有指标
     */
    @Override
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    /**
     * 根据代码获取指标
     */
    @Override
    public Metric getMetricByCode(String code) {
        Optional<Metric> optionalMetric = metricRepository.findByCode(code);
        return optionalMetric.orElse(null);
    }

    /**
     * 创建指标
     */
    @Override
    public Metric createMetric(Metric metric) {
        return metricRepository.save(metric);
    }

    /**
     * 更新指标
     */
    @Override
    public Metric updateMetric(String code, Metric metric) {
        Optional<Metric> optionalMetric = metricRepository.findByCode(code);
        if (optionalMetric.isPresent()) {
            Metric existingMetric = optionalMetric.get();
            existingMetric.setName(metric.getName());
            existingMetric.setExpression(metric.getExpression());
            existingMetric.setDescription(metric.getDescription());
            return metricRepository.save(existingMetric);
        } else {
            throw new RuntimeException("Metric not found: " + code);
        }
    }

    /**
     * 删除指标
     */
    @Override
    public void deleteMetric(String code) {
        Optional<Metric> optionalMetric = metricRepository.findByCode(code);
        if (optionalMetric.isPresent()) {
            metricRepository.delete(optionalMetric.get());
        } else {
            throw new RuntimeException("Metric not found: " + code);
        }
    }
}