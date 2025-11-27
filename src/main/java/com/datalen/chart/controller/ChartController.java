package com.datalen.chart.controller;

import com.datalen.chart.dto.ChartRequestDTO;
import com.datalen.chart.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 图表控制器
 */
@RestController
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    /**
     * 生成图表数据
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateChartData(@RequestBody ChartRequestDTO chartRequest) {
        Map<String, Object> result = chartService.generateChartData(chartRequest);
        return ResponseEntity.ok(result);
    }
}