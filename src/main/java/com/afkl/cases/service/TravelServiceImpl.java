package com.afkl.cases.service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.afkl.cases.constants.Constants;
import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;
import com.afkl.cases.exceptions.ResultNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TravelServiceImpl implements TravelService {

	private Environment env;

	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(TravelServiceImpl.class);

	@Autowired
	public TravelServiceImpl(RestTemplate restTemplate, Environment env) {
		this.env = env;
		this.restTemplate = restTemplate;
	}

	@Cacheable("airportsList")
	@Override
	public List<AirportsDto> findAirportDetails(MultiValueMap<String, String> params) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		UriComponents airportUriBuilder = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.AIRPORTS_URL))
				.queryParams(params).build();
		logger.debug("airportUriBuilder in getairports is " + airportUriBuilder.toString());

		try {

			String airportsJsonString = restTemplate.getForObject(airportUriBuilder.toString(), String.class);

			logger.debug("airportsJsonString is " + airportsJsonString);

			List<AirportsDto> airtportsList = objectMapper.readValue(
					objectMapper.readTree(airportsJsonString).findPath(Constants.LOCATIONS).toString(),
					new TypeReference<List<AirportsDto>>() {
					});

			airtportsList.sort(Comparator.comparing(AirportsDto::getCode));
			return airtportsList;

		} catch (HttpStatusCodeException e) {		
			logger.error("Airports List not available exception ", e);
			throw new ResultNotFoundException(" Airports List not available ");
		}

	}

	@Cacheable("faresDto")
	@Async("asyncExecutor")
	public CompletableFuture<FaresDto> getFareDetails(String origin, String destination,
			MultiValueMap<String, String> params) throws InterruptedException {
		logger.debug("Async getFareDetails starts");

		UriComponents travelFareUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.TRAVEL_FARE_URL)).path("/{origin}").path("/{destination}")
				.queryParams(params).buildAndExpand(origin, destination);

		return CompletableFuture
				.completedFuture(restTemplate.getForObject(travelFareUriBuilder.toString(), FaresDto.class));
	}

	@Cacheable("airportsDto")
	@Async("asyncExecutor")
	public CompletableFuture<AirportsDto> getAirportDetails(String airportCode, MultiValueMap<String, String> params)
			throws InterruptedException {
		logger.debug("getAirportDetails starts for airportCode " + airportCode);

		UriComponents originUriBuilder = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL))
				.path("/{origin}").queryParams(params).buildAndExpand(airportCode);

		return CompletableFuture
				.completedFuture(restTemplate.getForObject(originUriBuilder.toString(), AirportsDto.class));
	}

}
