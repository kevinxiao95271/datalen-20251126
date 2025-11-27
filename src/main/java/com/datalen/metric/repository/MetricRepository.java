package com.datalen.metric.repository;

import com.datalen.metric.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 指标Repository接口
 */
@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    /**
     * 根据指标代码查找指标
     */
    Optional<Metric> findByCode(String code);
}