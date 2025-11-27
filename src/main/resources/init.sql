-- 创建区域表
CREATE TABLE IF NOT EXISTS t_region (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    region_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建部门表
CREATE TABLE IF NOT EXISTS t_dept (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(50) NOT NULL,
    region_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (region_id) REFERENCES t_region(id)
);

-- 创建销售表
CREATE TABLE IF NOT EXISTS t_sales (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dept_id BIGINT,
    month VARCHAR(7) NOT NULL,
    sales DECIMAL(15, 2) NOT NULL,
    target DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dept_id) REFERENCES t_dept(id)
);

-- 插入测试数据
INSERT INTO t_region (region_name) VALUES ('华东'), ('华南'), ('华北');

INSERT INTO t_dept (dept_name, region_id) VALUES 
('上海销售部', 1),
('杭州销售部', 1),
('广州销售部', 2),
('深圳销售部', 2),
('北京销售部', 3),
('天津销售部', 3);

INSERT INTO t_sales (dept_id, month, sales, target) VALUES 
(1, '2024-01', 120000.00, 150000.00),
(1, '2024-02', 140000.00, 155000.00),
(1, '2024-03', 110000.00, 160000.00),
(2, '2024-01', 90000.00, 100000.00),
(2, '2024-02', 100000.00, 110000.00),
(2, '2024-03', 120000.00, 120000.00),
(3, '2024-01', 150000.00, 140000.00),
(3, '2024-02', 160000.00, 150000.00),
(3, '2024-03', 170000.00, 160000.00),
(4, '2024-01', 130000.00, 125000.00),
(4, '2024-02', 145000.00, 140000.00),
(4, '2024-03', 155000.00, 150000.00),
(5, '2024-01', 110000.00, 120000.00),
(5, '2024-02', 130000.00, 130000.00),
(5, '2024-03', 140000.00, 135000.00),
(6, '2024-01', 80000.00, 90000.00),
(6, '2024-02', 95000.00, 100000.00),
(6, '2024-03', 105000.00, 110000.00);