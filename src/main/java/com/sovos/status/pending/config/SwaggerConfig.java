package com.sovos.status.pending.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.sovos.status")).paths(PathSelectors.any()).build()
		.apiInfo(apiInfo()).useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Document API")
                .description("\"Documents API specifications\"")
                .version("1.0.0")
                .license("License of API")
                .licenseUrl("API license URL")
                .contact(new Contact("Status Pending Util", "contactURL", "Esteban.Contreras@sovos.com"))
                .build();
	}

}
