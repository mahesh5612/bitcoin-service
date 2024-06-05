package com.myorg.bitcoin.dto;

import java.util.Map;

public class CoinDeskHistoryResponseDto {

	private Map<String, Float> bpi;

	private String disclaimer;

	private Map<String, String> time;
	
	public Map<String, Float> getBpi() {
		return bpi;
	}

	public void setBpi(Map<String, Float> bpi) {
		this.bpi = bpi;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public Map<String, String> getTime() {
		return time;
	}

	public void setTime(Map<String, String> time) {
		this.time = time;
	}

}
