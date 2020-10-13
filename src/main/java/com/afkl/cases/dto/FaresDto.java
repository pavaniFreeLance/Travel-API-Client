package com.afkl.cases.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FaresDto {

	String amount;
	String currency;
	String origin;
	String originName;
	String destinationName;
	String destination;

	public FaresDto() {

	}

	public FaresDto(String amount, String currency, String originCode, String originName, String destinationName,
			String destinationCode) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.origin = originCode;
		this.originName = originName;
		this.destinationName = destinationName;
		this.destination = destinationCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "FaresDto [amount=" + amount + ", currency=" + currency + ", origin=" + origin + ", originName="
				+ originName + ", destinationName=" + destinationName + ", destination=" + destination + "]";
	}

}