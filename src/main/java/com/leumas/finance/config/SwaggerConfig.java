package com.leumas.finance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenAPI() {

        Info info = new Info();
        info.setTitle("Finance Manager");
        info.setVersion("1.0");
        info.setDescription("Finance Manager API");

        return new OpenAPI().info(info);
    }
}
