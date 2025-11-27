package com.datalen.metric.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 指标实体类
 */
@Data
@Entity
@Table(name = "t_metric")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metric_code", unique = true, nullable = false)
    private String code;

    @Column(name = "metric_name", nullable = false)
    private String name;

    @Column(name = "expression", nullable = false, columnDefinition = "TEXT")
    private String expression;

    @Column(name = "description")
    private String description;
}