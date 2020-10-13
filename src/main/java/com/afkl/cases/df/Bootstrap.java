package com.afkl.cases.df;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "com.afkl.cases")
public class Bootstrap {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Bootstrap.class, args);
	}

	@Bean
	@ConfigurationProperties("security.oauth2.client")
	public ClientCredentialsResourceDetails oAuthDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new OAuth2RestTemplate(oAuthDetails());
	}
	
	/*
	 * ModelMapper Bean used to convert Object to DTO and DTO to Object
	 */
	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return modelMapper;
	}

}
