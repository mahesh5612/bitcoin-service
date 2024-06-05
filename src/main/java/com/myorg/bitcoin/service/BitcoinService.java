package com.myorg.bitcoin.service;

import java.util.List;
import java.util.Map;

import com.myorg.bitcoin.dto.CurrencyResponseDto;
import com.myorg.bitcoin.dto.HistoryRequestDto;

public interface BitcoinService {

	List<CurrencyResponseDto> getSupportedCurrencies();

	Map<String, String> getCoindeskHistory(HistoryRequestDto historyRequestDto);

	Float getCurrencyRate(String currencyCode);

}
