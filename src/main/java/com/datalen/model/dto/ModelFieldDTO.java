package com.datalen.model.dto;

import lombok.Data;

/**
 * 模型字段DTO
 */
@Data
public class ModelFieldDTO {
    private String code;
    private String name;
    private String type;
    private String tableAlias;
    private String columnName;
    private String aggFunction;
    private Boolean isDimension;
    private Boolean isMeasure;
}