package com.datalen.metric.controller;

import com.datalen.metric.entity.Metric;
import com.datalen.metric.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 指标控制器
 */
@RestController
@RequestMapping("/metrics")
public class MetricController {

    @Autowired
    private MetricService metricService;

    /**
     * 获取所有指标
     */
    @GetMapping
    public ResponseEntity<List<Metric>> getAllMetrics() {
        List<Metric> metrics = metricService.getAllMetrics();
        return ResponseEntity.ok(metrics);
    }

    /**
     * 根据代码获取指标
     */
    @GetMapping("/{code}")
    public ResponseEntity<Metric> getMetricByCode(@PathVariable String code) {
        Metric metric = metricService.getMetricByCode(code);
        if (metric == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metric);
    }

    /**
     * 创建指标
     */
    @PostMapping
    public ResponseEntity<Metric> createMetric(@RequestBody Metric metric) {
        Metric createdMetric = metricService.createMetric(metric);
        return ResponseEntity.ok(createdMetric);
    }

    /**
     * 更新指标
     */
    @PutMapping("/{code}")
    public ResponseEntity<Metric> updateMetric(@PathVariable String code, @RequestBody Metric metric) {
        Metric updatedMetric = metricService.updateMetric(code, metric);
        return ResponseEntity.ok(updatedMetric);
    }

    /**
     * 删除指标
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteMetric(@PathVariable String code) {
        metricService.deleteMetric(code);
        return ResponseEntity.noContent().build();
    }
}