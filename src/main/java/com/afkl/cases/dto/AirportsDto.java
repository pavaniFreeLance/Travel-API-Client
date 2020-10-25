package com.afkl.cases.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportsDto {

	String code;
	String name;
	String description;

	public AirportsDto() {

	}

	public AirportsDto(String code, String name, String description) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AirportsDto [code=" + code + ", name=" + name + ", description=" + description + "]";
	}

}
