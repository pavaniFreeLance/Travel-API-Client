package com.afkl.cases.service;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	private final String BASE_PACKAGE = "com.afkl.cases";
	/*Creating Docket*/
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE)).build()
				.apiInfo(this.metaData());
	}

	/*
	 * Setting Meta data in swagger 
	 */
	private ApiInfo metaData() {
		return new ApiInfo("Travel API Client", "Rest api for providing Air Travel Fares details", "1.0", "Free to use",
				new Contact("Pavani", "URL", "Pavani.Bandi@gmail.com"), "API Licence",
				"URL", Collections.emptyList());

	}
}