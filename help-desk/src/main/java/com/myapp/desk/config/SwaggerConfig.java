package com.myapp.desk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${help.desk.openapi.dev-url:http://localhost:9090}")
    private String devUrl;

    @Value("${help.desk.openapi.prod-url:https://help-desk-api.com}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("support@helpdesk.com");
        contact.setName("Help Desk Team");
        contact.setUrl("https://www.helpdesk.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Help Desk Management API")
                .version("1.0")
                .contact(contact)
                .description("This API provides endpoints for managing tickets, instruments, trades, and agents in the Help Desk system. It includes Redis caching for improved performance.")
                .termsOfService("https://www.helpdesk.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
