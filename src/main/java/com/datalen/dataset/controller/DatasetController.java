package com.datalen.dataset.controller;

import com.datalen.dataset.dto.DatasetCreateRequestDTO;
import com.datalen.dataset.dto.DatasetQueryRequestDTO;
import com.datalen.dataset.dto.DatasetQueryResponseDTO;
import com.datalen.dataset.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 数据集控制器
 */
@RestController
@RequestMapping("/datasets")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    /**
     * 创建Dataset
     */
    @PostMapping
    public ResponseEntity<?> createDataset(@RequestBody DatasetCreateRequestDTO request) {
        String datasetId = datasetService.createDataset(request);
        return ResponseEntity.ok().body("{\"id\": \"" + datasetId + "\", \"status\": \"ok\"}");
    }

    /**
     * 更新Dataset
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDataset(@PathVariable String id, @RequestBody DatasetCreateRequestDTO request) {
        datasetService.updateDataset(id, request);
        return ResponseEntity.ok().body("{\"id\": \"" + id + "\", \"status\": \"ok\"}");
    }

    /**
     * 删除Dataset
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDataset(@PathVariable String id) {
        datasetService.deleteDataset(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询Dataset数据
     */
    @PostMapping("/{id}/query")
    public ResponseEntity<DatasetQueryResponseDTO> queryDataset(@PathVariable String id, @RequestBody DatasetQueryRequestDTO request) {
        DatasetQueryResponseDTO response = datasetService.queryDataset(id, request);
        return ResponseEntity.ok(response);
    }
}