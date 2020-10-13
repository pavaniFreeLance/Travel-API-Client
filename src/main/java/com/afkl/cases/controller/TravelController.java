package com.afkl.cases.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.constants.Constants;
import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;
import com.afkl.cases.service.TravelService;

@RestController
@RequestMapping(value = "/travel", produces = "application/json")
public class TravelController {

	private TravelService travelService;

	@Autowired
	public TravelController(TravelService travelService) {
		this.travelService = travelService;
	}

	@GetMapping(Constants.AIRPORTS)
	public ResponseEntity<List<AirportsDto>> getAirports() {

		List<AirportsDto> airportsDto = this.travelService.findAirportDetails();

		return (Objects.nonNull(airportsDto)) ? ResponseEntity.ok(airportsDto) : ResponseEntity.notFound().build();

	}

	@GetMapping(Constants.FARES)
	public ResponseEntity<FaresDto> getTravelFare(@PathVariable String origin, @PathVariable String destination,
			@RequestParam Optional<String> currency) {

		FaresDto faresDto = travelService.calculateFareDetails(origin, destination);

		return (Objects.nonNull(faresDto)) ? ResponseEntity.ok(faresDto) : ResponseEntity.notFound().build();

	}
}
