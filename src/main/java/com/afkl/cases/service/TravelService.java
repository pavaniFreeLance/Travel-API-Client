package com.afkl.cases.service;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.MultiValueMap;

import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TravelService {

	 @Retryable(value = { ConnectException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	List<AirportsDto> findAirportDetails(MultiValueMap<String, String> params) throws JsonProcessingException;

	 @Retryable(value = { ConnectException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	 CompletableFuture<FaresDto> getFareDetails(String Origin, String destination, MultiValueMap<String, String> params) throws InterruptedException;
	 
	 @Retryable(value = { ConnectException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	 CompletableFuture<AirportsDto> getAirportDetails(String airportCode,MultiValueMap<String, String> params) throws InterruptedException; 
}
