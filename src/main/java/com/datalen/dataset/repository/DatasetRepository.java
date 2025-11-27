package com.datalen.dataset.repository;

import com.datalen.dataset.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据集Repository接口
 */
@Repository
public interface DatasetRepository extends JpaRepository<Dataset, String> {
}
