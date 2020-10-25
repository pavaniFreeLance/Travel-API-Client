package com.afkl.cases.controller;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;
import com.afkl.cases.exceptions.ResultNotFoundException;
import com.afkl.cases.service.TravelService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/travel", produces = "application/json")
public class TravelController {

	private TravelService travelService;

	private static final Logger logger = LoggerFactory.getLogger(TravelController.class);

	@Autowired
	public TravelController(TravelService travelService) {
		this.travelService = travelService;
	}

	@GetMapping("/airports")
	public Callable<ResponseEntity<List<AirportsDto>>> getAirports(@RequestParam MultiValueMap<String, String> params)
			throws JsonProcessingException {

		return () -> {
			List<AirportsDto> airportsDto = this.travelService.findAirportDetails(params);

			return (Objects.nonNull(airportsDto)) ? ResponseEntity.ok(airportsDto) : ResponseEntity.notFound().build();
		};

	}

	@GetMapping("/fares/{origin}/{destination}")
	public Callable<ResponseEntity<FaresDto>> getFare(@PathVariable String origin, @PathVariable String destination,
			@RequestParam MultiValueMap<String, String> params) throws InterruptedException, ExecutionException {

		logger.debug("Getting fare details from origin " + origin + " to destination " + destination);
		return () -> {

			CompletableFuture<FaresDto> faresDto = travelService.getFareDetails(origin, destination, params);

			CompletableFuture<AirportsDto> originAirportDto = travelService.getAirportDetails(origin, params);

			CompletableFuture<AirportsDto> destinationAirportDto = travelService.getAirportDetails(destination, params);

			try {
				// Wait until they are all done
				CompletableFuture.allOf(faresDto, originAirportDto, destinationAirportDto).join();
				logger.debug("wait unit join ");
				faresDto.get().setOriginName(originAirportDto.get().getName());
				faresDto.get().setDestinationName(destinationAirportDto.get().getName());
				return ResponseEntity.ok(faresDto.get());
			} catch (CompletionException ex) {
				logger.error("Completion exception ");
				if (ex.getCause() instanceof HttpStatusCodeException) {
					throw new ResultNotFoundException("Invalid origin or destination given");
				} else {
					throw new Exception(ex.getCause());
				}

			}

		};
	}
}
