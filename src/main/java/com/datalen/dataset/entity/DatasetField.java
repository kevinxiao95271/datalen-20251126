package com.datalen.dataset.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 数据集字段实体类
 */
@Data
@Entity
@Table(name = "t_dataset_field")
public class DatasetField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    @Column(name = "field_code")
    private String code;

    @Column(name = "field_alias")
    private String alias;

    @Column(name = "agg_function")
    private String aggFunction;

}