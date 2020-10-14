package com.afkl.cases.service;

import java.util.List;
import java.util.Optional;

import com.afkl.cases.dto.AirportsDto;
import com.afkl.cases.dto.FaresDto;

public interface TravelService {

	public List<AirportsDto> findAirportDetails();

	public FaresDto calculateFareDetails(String origin, String destination, Optional<String> currency);

}
