package com.datalen.model.controller;

import com.datalen.model.dto.ModelDTO;
import com.datalen.model.dto.ModelFieldDTO;
import com.datalen.model.dto.JoinDTO;
import com.datalen.model.entity.Model;
import com.datalen.model.entity.ModelField;
import com.datalen.model.entity.Join;
import com.datalen.model.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 模型控制器
 */
@RestController
@RequestMapping("/models")
public class ModelController {

    @Autowired
    private ModelService modelService;

    /**
     * 列出所有模型
     */
    @GetMapping
    public ResponseEntity<List<ModelDTO>> listModels() {
        List<Model> models = modelService.getAllModels();
        List<ModelDTO> modelDTOs = models.stream().map(this::convertToModelDTO).collect(Collectors.toList());
        return ResponseEntity.ok(modelDTOs);
    }

    /**
     * 获取模型详细结构
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModelDTO> getModel(@PathVariable String id) {
        Model model = modelService.getModelById(id);
        if (model == null) {
            return ResponseEntity.notFound().build();
        }
        ModelDTO modelDTO = convertToModelDTO(model);
        return ResponseEntity.ok(modelDTO);
    }

    /**
     * 转换Model实体到DTO
     */
    private ModelDTO convertToModelDTO(Model model) {
        ModelDTO dto = new ModelDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setBaseTable(model.getBaseTable());

        // 转换字段
        List<ModelFieldDTO> fieldDTOs = model.getFields().stream().map(field -> {
            ModelFieldDTO fieldDTO = new ModelFieldDTO();
            fieldDTO.setCode(field.getCode());
            fieldDTO.setName(field.getName());
            fieldDTO.setType(field.getType());
            fieldDTO.setTableAlias(field.getTableAlias());
            fieldDTO.setColumnName(field.getColumnName());
            fieldDTO.setAggFunction(field.getAggFunction());
            fieldDTO.setIsDimension(field.getIsDimension());
            fieldDTO.setIsMeasure(field.getIsMeasure());
            return fieldDTO;
        }).collect(Collectors.toList());
        dto.setFields(fieldDTOs);

        // 转换连接
        List<JoinDTO> joinDTOs = model.getJoins().stream().map(join -> {
            JoinDTO joinDTO = new JoinDTO();
            joinDTO.setJoinTable(join.getJoinTable());
            joinDTO.setJoinAlias(join.getJoinAlias());
            joinDTO.setJoinType(join.getJoinType());
            joinDTO.setLeftColumn(join.getLeftColumn());
            joinDTO.setRightColumn(join.getRightColumn());
            return joinDTO;
        }).collect(Collectors.toList());
        dto.setJoins(joinDTOs);

        return dto;
    }
}