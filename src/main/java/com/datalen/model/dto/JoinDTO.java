package com.datalen.model.dto;

import lombok.Data;

/**
 * 模型连接DTO
 */
@Data
public class JoinDTO {
    private String joinTable;
    private String joinAlias;
    private String joinType;
    private String leftColumn;
    private String rightColumn;
}