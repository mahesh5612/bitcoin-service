package com.myorg.bitcoin.dto;

import java.util.Map;

public class CurrencyPriceResponseDto {

	private Map<String, String> time;

	private String disclaimer;

	private Map<String, CurrencyRate> bpi;

	public Map<String, String> getTime() {
		return time;
	}

	public void setTime(Map<String, String> time) {
		this.time = time;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public Map<String, CurrencyRate> getBpi() {
		return bpi;
	}

	public void setBpi(Map<String, CurrencyRate> bpi) {
		this.bpi = bpi;
	}

}
