package com.afkl.cases.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.afkl.cases.constants.Constants;
import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;
import com.afkl.cases.exceptions.ErrorResponse;
import com.afkl.cases.service.TravelService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class TravelControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private Environment env;

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	TravelService travelService;

	TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {

	}

	@Test
	public void testGetAirports() throws Exception {

		URL res = getClass().getClassLoader().getResource("airportsList.txt");
		Path path = Paths.get(res.toURI());
		String content = Files.readString(path, StandardCharsets.US_ASCII);

		Mockito.when(restTemplate.getForObject(Matchers.anyString(), Matchers.eq(String.class))).thenReturn(content);
		String result = testRestTemplate.getForObject("http://localhost:" + port + "/travel/airports", String.class);

		List<AirportsDto> airtportsList = objectMapper.readValue(result, new TypeReference<List<AirportsDto>>() {
		});
		assertEquals(25, airtportsList.size());

	}

	@Test
	public void testGetFare() {

		FaresDto expectedFares = new FaresDto("1123", "EUR", "YOW", "", "", "BBA");
		AirportsDto expectedOrigin = new AirportsDto("YOW", "Yow Example Airport", "Origin Example world");
		AirportsDto expectedDestination = new AirportsDto("BBA", "BBA Example Airport", "desti Example world");

		UriComponents travelFareUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.TRAVEL_FARE_URL)).path("/{origin}").path("/{destination}")
				.queryParams(null).buildAndExpand("YOW", "BBA");

		Mockito.when(restTemplate.getForObject(travelFareUriBuilder.toString(), FaresDto.class))
				.thenReturn(expectedFares);

		UriComponents originUriBuilder = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL))
				.path("/{origin}").queryParams(null).buildAndExpand("YOW");
		UriComponents destiantionUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL)).path("/{destination}").queryParams(null)
				.buildAndExpand("BBA");

		Mockito.when(restTemplate.getForObject(originUriBuilder.toString(), AirportsDto.class))
				.thenReturn(expectedOrigin);
		Mockito.when(restTemplate.getForObject(destiantionUriBuilder.toString(), AirportsDto.class))
				.thenReturn(expectedDestination);

		FaresDto resultFaresDto = testRestTemplate.getForObject("http://localhost:" + port + "/travel/fares/YOW/BBA",
				FaresDto.class);

		assertNotNull(resultFaresDto);
		assertEquals(expectedFares.getAmount(), resultFaresDto.getAmount());
		assertEquals(expectedOrigin.getName(), resultFaresDto.getOriginName());
		assertEquals(expectedDestination.getName(), resultFaresDto.getDestinationName());

	}

	@Test
	public void testGetFare_ResultNotFound() {

		FaresDto expectedFares = new FaresDto("1123", "EUR", "YOW", "", "", "BBA");
		AirportsDto expectedOrigin = new AirportsDto("YOW", "Yow Example Airport", "Origin Example world");

		UriComponents travelFareUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.TRAVEL_FARE_URL)).path("/{origin}").path("/{destination}")
				.queryParams(null).buildAndExpand("YOW", "KKA");

		Mockito.when(restTemplate.getForObject(travelFareUriBuilder.toString(), FaresDto.class))
				.thenReturn(expectedFares);

		UriComponents originUriBuilder = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL))
				.path("/{origin}").queryParams(null).buildAndExpand("YOW");
		UriComponents destiantionUriBuilder = UriComponentsBuilder
				.fromHttpUrl(env.getProperty(Constants.SINGLE_AIRPORT_URL)).path("/{destination}").queryParams(null)
				.buildAndExpand("KKA");

		Mockito.when(restTemplate.getForObject(originUriBuilder.toString(), AirportsDto.class))
				.thenReturn(expectedOrigin);
		Mockito.when(restTemplate.getForObject(destiantionUriBuilder.toString(), AirportsDto.class))
				.thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

		ErrorResponse error = testRestTemplate.getForObject("http://localhost:" + port + "/travel/fares/YOW/KKA",
				ErrorResponse.class);

		assertNotNull(error);
		assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());

	}
}
