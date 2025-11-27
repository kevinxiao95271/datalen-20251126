package com.datalen.model.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 模型连接关系实体类
 */
@Data
@Entity
@Table(name = "t_model_join")
public class Join {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "join_table")
    private String joinTable;

    @Column(name = "join_alias")
    private String joinAlias;

    @Column(name = "join_type")
    private String joinType;

    @Column(name = "left_column")
    private String leftColumn;

    @Column(name = "right_column")
    private String rightColumn;

}