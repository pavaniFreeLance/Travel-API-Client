package com.afkl.cases.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.afkl.cases.constants.Constants;
import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.exceptions.ResultNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(MockitoJUnitRunner.class)
public class TravelServiceImplTest {

	@InjectMocks
	private TravelServiceImpl travelServiceImpl;

	@Mock
	private RestTemplate restTemplate;

	String content = null;

	@Mock
	private Environment env;

	@Before
	public void setUp() {

	}

	@Test
	public void testFindAirportDetails() throws Exception {

		URL res = getClass().getClassLoader().getResource("airportsList.txt");
		Path path = Paths.get(res.toURI());
		String content = Files.readString(path, StandardCharsets.US_ASCII);

		Mockito.when(restTemplate.getForObject(Matchers.anyString(), Matchers.eq(String.class))).thenReturn(content);
		Mockito.when(env.getProperty(Constants.AIRPORTS_URL)).thenReturn("http://localhost:8081/airports");

		List<AirportsDto> airportsDtoList = travelServiceImpl.findAirportDetails(null);

		verify(restTemplate, times(1)).getForObject(Matchers.anyString(), Matchers.eq(String.class));
		assertNotNull(airportsDtoList);
		assertEquals(25, airportsDtoList.size());

	}

	@Test
	public void testFindAirportDetailsNoResultsReturnedFromServer() throws JsonProcessingException {
		Mockito.when(restTemplate.getForObject(Matchers.anyString(), Matchers.eq(String.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		Mockito.when(env.getProperty(Constants.AIRPORTS_URL)).thenReturn("http://localhost:8081/airports");

		Throwable exception = assertThrows(ResultNotFoundException.class,
				() -> travelServiceImpl.findAirportDetails(null));
		assertEquals("Airports List not available", exception.getMessage());

	}

	/*
	 * TODO more unit test cases
	 * 
	 * @Test public void testGetFareDetails() {
	 * 
	 * }
	 * 
	 * @Test public void testGetAirportDetails() { }
	 */

}
