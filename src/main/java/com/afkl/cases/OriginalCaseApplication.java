package com.afkl.cases;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRetry
@EnableCaching
public class OriginalCaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OriginalCaseApplication.class, args);
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
	
	
	/*@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
	        OAuth2ProtectedResourceDetails details) {
	    return new OAuth2RestTemplate(details, oauth2ClientContext);
	}
	*/
	
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
