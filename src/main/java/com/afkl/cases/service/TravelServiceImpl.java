package com.afkl.cases.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.afkl.cases.constants.Constants;
import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TravelServiceImpl implements TravelService {

	@Autowired
	private Environment env;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(TravelServiceImpl.class);

	@Override
	public List<AirportsDto> findAirportDetails() {

		ObjectMapper objectMapper = new ObjectMapper();

		List<AirportsDto> airportsDtoList = null;
		UriComponents originUriBuilder = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.AIRPORTS_URL))
				.build();

		// Rest call to get airport details
		try {
			CompletableFuture<String> airportsJsonString = fetchDetails(originUriBuilder.toString());

			airportsDtoList = objectMapper.readValue(
					objectMapper.readTree(airportsJsonString.get()).findPath(Constants.LOCATIONS).toString(),
					new TypeReference<List<AirportsDto>>() {
					});
		} catch (Exception e) {
			logger.error("Parsing exception ", e.getMessage());
		}

		return airportsDtoList;
	}

	@Override
	public FaresDto calculateFareDetails(String origin, String destination,Optional<String> currency) {
		String currencyParam = currency.isPresent()? currency.get() :null;
		UriComponents travelFareUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.TRAVEL_FARE_URL)).path("/{origin}").path("/{destination}")
				.queryParam("currency",currencyParam)
				.buildAndExpand(origin, destination);

		UriComponents originUriBuilder = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL))
				.path("/{origin}").buildAndExpand(origin);

		UriComponents destinationUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL)).path("/{destination}")
				.buildAndExpand(destination);

		ObjectMapper objectMapper = new ObjectMapper();
		FaresDto faresDto = null;

		CompletableFuture<String> fareDetailsString = fetchDetails(travelFareUriBuilder.toUri().toString());
		CompletableFuture<String> originDetailsString = fetchDetails(originUriBuilder.toUri().toString());
		CompletableFuture<String> destinationDetailsString = fetchDetails(destinationUriBuilder.toUri().toString());
		// Wait until they are all done
	    CompletableFuture.allOf(fareDetailsString,originDetailsString,destinationDetailsString).join();

		try {

			faresDto = objectMapper.readValue(fareDetailsString.get(), FaresDto.class);
			faresDto.setOriginName(objectMapper.readValue(originDetailsString.get(), AirportsDto.class).getName());
			faresDto.setDestinationName(
					objectMapper.readValue(destinationDetailsString.get(), AirportsDto.class).getName());

		} catch (Exception e) {
			logger.error("Parsing error " + e.getMessage());

		}

		return faresDto;
	}

	/**
	 * Rest call
	 * 
	 * @param url
	 * @return
	 */
	@Async
	private CompletableFuture<String> fetchDetails(String url) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json;charset=utf-8");
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return CompletableFuture.completedFuture(response.getBody());

	}

}
