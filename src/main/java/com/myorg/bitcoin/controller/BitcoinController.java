package com.myorg.bitcoin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.bitcoin.dto.CurrencyResponseDto;
import com.myorg.bitcoin.dto.ErrorResponseDto;
import com.myorg.bitcoin.dto.HistoryRequestDto;
import com.myorg.bitcoin.dto.HistoryResponseDto;
import com.myorg.bitcoin.exception.InvalidDateException;
import com.myorg.bitcoin.service.impl.BitcoinServiceImpl;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "REST APIs for Bitcoin service", description = "REST APIs for getting currencies and history information for bitcoin")
@RestController
@RequestMapping("/api/v1/bitcoin")
public class BitcoinController {

	@Autowired
	private BitcoinServiceImpl bitcoinService;

	@Operation(summary = "Fetch currencies", description = "REST API to fetch all supported currencies")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP status internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/currencies")
	public ResponseEntity<List<CurrencyResponseDto>> getAllCurrencies() {
		return new ResponseEntity<>(bitcoinService.getSupportedCurrencies(), HttpStatus.OK);
	}

	@Operation(summary = "Fetch history", description = "REST API to fetch history for bitcoin by taking input of start, end date and output currency")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "400", description = "HTTP bad request or validation errors", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "HTTP status internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PostMapping("/history")
	public ResponseEntity<HistoryResponseDto> getHistory(@RequestBody @Valid HistoryRequestDto historyRequestDto) {
		validateDates(historyRequestDto.getStart(), historyRequestDto.getEnd());
		if (StringUtils.isBlank(historyRequestDto.getOutputCurrency())) {
			historyRequestDto.setOutputCurrency("USD");
		}
		Map<String, String> bpi = bitcoinService.getCoindeskHistory(historyRequestDto);
		return new ResponseEntity<>(
				new HistoryResponseDto(bpi, StringUtils.isBlank(historyRequestDto.getOutputCurrency()) ? "USD"
						: historyRequestDto.getOutputCurrency()),
				HttpStatus.OK);
	}

	private void validateDates(String start, String end) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			Date startDate = dateFormat.parse(start);
			Date endDate = dateFormat.parse(end);
			if (startDate.after(endDate)) {
				throw new InvalidDateException("Start date should be before the end date");
			}
		} catch (ParseException ex) {
			throw new InvalidDateException("Date format is incorrect. It should be yyyy-MM-dd");
		}
	}

}
