package com.datalen.model.dto;

import lombok.Data;
import java.util.List;

/**
 * 模型DTO
 */
@Data
public class ModelDTO {
    private String id;
    private String name;
    private String description;
    private String baseTable;
    private List<ModelFieldDTO> fields;
    private List<JoinDTO> joins;
}