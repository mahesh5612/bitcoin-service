package com.myorg.bitcoin.dto;

public class CurrencyResponseDto {

	private String currency;
	private String country;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "CurrencyResponseDto [currency=" + currency + ", country=" + country + "]";
	}

}
