package com.datalen.dataset.dto;

import lombok.Data;
import java.util.List;

/**
 * 创建数据集请求DTO
 */
@Data
public class DatasetCreateRequestDTO {
    private String id;
    private String name;
    private String modelId;
    private List<DatasetFieldDTO> fields;
    private List<String> groups;
    private List<DatasetFilterDTO> filters;
    private String sqlText;

    @Data
    public static class DatasetFieldDTO {
        private String code;
        private String alias;
        private String aggFunction;
    }

    @Data
    public static class DatasetFilterDTO {
        private String fieldCode;
        private String operator;
        private String filterValue;
    }
}