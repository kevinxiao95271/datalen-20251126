package com.datalen.model.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 模型字段实体类
 */
@Data
@Entity
@Table(name = "t_model_field")
public class ModelField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "field_code")
    private String code;

    @Column(name = "field_name")
    private String name;

    @Column(name = "field_type")
    private String type;

    @Column(name = "table_alias")
    private String tableAlias;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "agg_function")
    private String aggFunction;

    @Column(name = "is_dimension")
    private Boolean isDimension;

    @Column(name = "is_measure")
    private Boolean isMeasure;

}