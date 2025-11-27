package com.datalen.chart.service;

import com.datalen.chart.dto.ChartRequestDTO;

import java.util.Map;
import java.util.List;

/**
 * 图表服务接口
 */
public interface ChartService {
    /**
     * 生成图表数据
     */
    Map<String, Object> generateChartData(ChartRequestDTO chartRequest);
}