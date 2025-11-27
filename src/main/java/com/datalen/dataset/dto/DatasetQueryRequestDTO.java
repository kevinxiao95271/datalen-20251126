package com.datalen.dataset.dto;

import lombok.Data;

/**
 * 数据集查询请求DTO
 */
@Data
public class DatasetQueryRequestDTO {
    private Integer limit;
    private Integer offset;
}