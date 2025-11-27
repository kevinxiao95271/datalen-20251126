package com.datalen.dataset.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * 数据集实体类
 */
@Data
@Entity
@Table(name = "t_dataset")
public class Dataset {

    @Id
    @Column(name = "dataset_id")
    private String id;

    @Column(name = "dataset_name")
    private String name;

    @Column(name = "model_id")
    private String modelId;

    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DatasetField> fields;

    @ElementCollection
    @CollectionTable(name = "t_dataset_groups", joinColumns = @JoinColumn(name = "dataset_id"))
    @Column(name = "group_field")
    private List<String> groups;

    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DatasetFilter> filters;

    @Column(name = "sql_text", columnDefinition = "TEXT")
    private String sqlText;

}