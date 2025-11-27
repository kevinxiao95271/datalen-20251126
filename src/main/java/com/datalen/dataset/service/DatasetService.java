package com.datalen.dataset.service;

import com.datalen.dataset.dto.DatasetCreateRequestDTO;
import com.datalen.dataset.dto.DatasetQueryRequestDTO;
import com.datalen.dataset.dto.DatasetQueryResponseDTO;

/**
 * 数据集服务接口
 */
public interface DatasetService {
    /**
     * 创建数据集
     */
    String createDataset(DatasetCreateRequestDTO request);

    /**
     * 更新数据集
     */
    void updateDataset(String id, DatasetCreateRequestDTO request);

    /**
     * 删除数据集
     */
    void deleteDataset(String id);

    /**
     * 查询数据集
     */
    DatasetQueryResponseDTO queryDataset(String id, DatasetQueryRequestDTO request);
}