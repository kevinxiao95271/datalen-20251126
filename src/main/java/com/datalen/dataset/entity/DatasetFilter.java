package com.datalen.dataset.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 数据集过滤条件实体类
 */
@Data
@Entity
@Table(name = "t_dataset_filter")
public class DatasetFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    @Column(name = "field_code")
    private String fieldCode;

    @Column(name = "operator")
    private String operator;

    @Column(name = "filter_value")
    private String filterValue;

}