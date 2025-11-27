package com.datalen.model.service;

import com.datalen.model.entity.Model;

import java.util.List;

/**
 * 模型服务接口
 */
public interface ModelService {
    /**
     * 获取所有模型
     */
    List<Model> getAllModels();

    /**
     * 根据ID获取模型
     */
    Model getModelById(String id);
}