package com.datalen.dataset.dto;

import lombok.Data;
import java.util.List;

/**
 * 数据集查询响应DTO
 */
@Data
public class DatasetQueryResponseDTO {
    private List<String> columns;
    private List<List<Object>> rows;
    private Integer total;
}