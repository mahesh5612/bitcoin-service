package com.myorg.bitcoin.dto;

public class CurrencyRate {

	private String code;
	private String rate;
	private String description;
	private Float rate_float;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getRate_float() {
		return rate_float;
	}

	public void setRate_float(Float rate_float) {
		this.rate_float = rate_float;
	}

}
