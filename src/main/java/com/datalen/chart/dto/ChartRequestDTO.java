package com.datalen.chart.dto;

import lombok.Data;
import java.util.List;

/**
 * 图表请求DTO
 */
@Data
public class ChartRequestDTO {
    private String datasetId;
    private List<String> dimensions;
    private List<MetricRequestDTO> metrics;
    private String chartType;

    @Data
    public static class MetricRequestDTO {
        private String code;
        private String alias;
    }
}