package com.myorg.bitcoin.dto;

import java.util.Map;

public class HistoryResponseDto {

	private Map<String, String> bpi;

	private String outputCurrency;

	public HistoryResponseDto() {

	}

	public HistoryResponseDto(Map<String, String> bpi, String outputCurrency) {
		super();
		this.bpi = bpi;
		this.outputCurrency = outputCurrency;
	}

	public Map<String, String> getBpi() {
		return bpi;
	}

	public void setBpi(Map<String, String> bpi) {
		this.bpi = bpi;
	}

	public String getOutputCurrency() {
		return outputCurrency;
	}

	public void setOutputCurrency(String outputCurrency) {
		this.outputCurrency = outputCurrency;
	}

}
