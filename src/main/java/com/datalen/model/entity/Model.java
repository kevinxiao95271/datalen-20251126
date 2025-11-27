package com.datalen.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * 数据模型实体类
 */
@Data
@Entity
@Table(name = "t_model")
public class Model {

    @Id
    @Column(name = "model_id")
    private String id;

    @Column(name = "model_name")
    private String name;

    @Column(name = "model_desc")
    private String description;

    @Column(name = "base_table")
    private String baseTable;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ModelField> fields;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Join> joins;

}