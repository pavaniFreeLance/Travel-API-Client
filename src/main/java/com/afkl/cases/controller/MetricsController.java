package com.afkl.cases.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.bean.MetricBean;
import com.afkl.cases.dto.MetricBeanDto;
import com.afkl.cases.service.MetricService;

@RestController
@RequestMapping(value = "/metrics", produces = "application/json")
public class MetricsController {

	private MetricService metricService;

	private ModelMapper modelMapper;

	@Autowired
	public MetricsController(MetricService metricService, ModelMapper modelMapper) {
		this.metricService = metricService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<MetricBeanDto> getRestApiMetricDetails() {
		return new ResponseEntity<>(convertBeanToDto(metricService.getFullMetric()), HttpStatus.OK);

	}

	/*
	 * Converting of Entity Object to DTO object
	 * 
	 * @param Customer
	 * 
	 * @return CustomerDto
	 */
	private MetricBeanDto convertBeanToDto(MetricBean metricBean) {

		return modelMapper.map(metricBean, MetricBeanDto.class);

	}

}
