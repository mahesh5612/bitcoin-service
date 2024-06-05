package com.myorg.bitcoin.dto;

import jakarta.validation.constraints.NotBlank;

public class HistoryRequestDto {

	@NotBlank(message = "Start date must not be blank")
	private String start;

	@NotBlank(message = "End date must not be blank")
	private String end;

	private String outputCurrency;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getOutputCurrency() {
		return outputCurrency;
	}

	public void setOutputCurrency(String outputCurrency) {
		this.outputCurrency = outputCurrency;
	}

}
