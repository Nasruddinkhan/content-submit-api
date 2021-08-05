package com.mypractice.content.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(name = "contentapi", scheme = "basic", paramName = "Authorization", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Content API", version = "2.0", description = "Content Information"))

@SpringBootApplication
public class ContentSubmitApiApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ContentSubmitApiApplication.class, args);
    }

}
