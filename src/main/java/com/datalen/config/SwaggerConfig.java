package com.datalen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * 使用SpringDoc OpenAPI 3.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("数据可视化图表设计器API")
                        .version("1.0.0")
                        .description("图表设计器后端服务API文档，提供模型管理和数据集管理功能")
                        .termsOfService("http://swagger.io/terms/")
                );
    }
}
