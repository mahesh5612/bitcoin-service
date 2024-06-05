package com.myorg.bitcoin.service.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myorg.bitcoin.config.CoinbaseApiPaths;
import com.myorg.bitcoin.dto.CoinDeskHistoryResponseDto;
import com.myorg.bitcoin.dto.CurrencyPriceResponseDto;
import com.myorg.bitcoin.dto.CurrencyRate;
import com.myorg.bitcoin.dto.CurrencyResponseDto;
import com.myorg.bitcoin.dto.HistoryRequestDto;
import com.myorg.bitcoin.service.BitcoinService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BitcoinServiceImpl implements BitcoinService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CoinbaseApiPaths apiPaths;

	@Override
	public List<CurrencyResponseDto> getSupportedCurrencies() {
		String url = apiPaths.getBase().concat(apiPaths.getCurrencies());
		log.info("Currencies URL is: {}", url);
		ResponseEntity<List<CurrencyResponseDto>> exchange = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CurrencyResponseDto>>() {
				});
		List<CurrencyResponseDto> response = exchange.getBody();
		log.info("Currencies API response: {}", response);
		return response;
	}

	@Override
	public Map<String, String> getCoindeskHistory(HistoryRequestDto historyRequestDto) {
		String historyUrl = apiPaths.getBase().concat(apiPaths.getHistory()) + "?start=" + historyRequestDto.getStart()
				+ "&end=" + historyRequestDto.getEnd();
		log.info("History URL is: {}", historyUrl);
		ResponseEntity<CoinDeskHistoryResponseDto> exchange = restTemplate.exchange(historyUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<CoinDeskHistoryResponseDto>() {
				});
		CoinDeskHistoryResponseDto response = exchange.getBody();
		log.info("History API response: {}", response);
		if ("USD".equalsIgnoreCase(historyRequestDto.getOutputCurrency())) {
			Map<String, Float> bpiUSDMap = response.getBpi();
			return transformMap(bpiUSDMap);
		} else {
			return mutateResponse(response.getBpi(), historyRequestDto.getOutputCurrency());
		}
	}

	private Map<String, String> mutateResponse(Map<String, Float> bpiUSDMap, String outputCurrency) {
		Float currencyRate = getCurrencyRate(outputCurrency);
		Map<String, Float> bpiOutputCurrencyMap = new LinkedHashMap<>();
		for (Map.Entry<String, Float> entry : bpiUSDMap.entrySet()) {
			Float modifiedValue = entry.getValue() * currencyRate;
			bpiOutputCurrencyMap.put(entry.getKey(), modifiedValue);
		}
		return transformMap(bpiOutputCurrencyMap);

	}

	private Map<String, String> transformMap(Map<String, Float> bpiMap) {
		Float maxValue = Collections.max(bpiMap.values());
		Float minValue = Collections.min(bpiMap.values());
		Map<String, String> bpiTransformedMap = new LinkedHashMap<>();
		for (Map.Entry<String, Float> entry : bpiMap.entrySet()) {
			Float modifiedValue = entry.getValue();
			if (maxValue == modifiedValue) {
				bpiTransformedMap.put(entry.getKey(), modifiedValue + "(High)");
			} else if (minValue == modifiedValue) {
				bpiTransformedMap.put(entry.getKey(), modifiedValue + "(Low)");
			} else {
				bpiTransformedMap.put(entry.getKey(), modifiedValue + "");
			}
		}
		return bpiTransformedMap;
	}

	@Override
	public Float getCurrencyRate(String currencyCode) {
		String currencyUrl = apiPaths.getBase().concat(apiPaths.getCurrentprice()) + currencyCode + ".json";
		log.info("Currency price URL is: {}", currencyUrl);
		ResponseEntity<CurrencyPriceResponseDto> exchange = restTemplate.exchange(currencyUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<CurrencyPriceResponseDto>() {
				});
		CurrencyPriceResponseDto currencyPriceResponse = exchange.getBody();
		CurrencyRate usd = currencyPriceResponse.getBpi().get("USD");
		CurrencyRate outputCurrency = currencyPriceResponse.getBpi().get(currencyCode);
		log.info("USD rate for output currency {} is: {}", currencyCode,
				outputCurrency.getRate_float() / usd.getRate_float());
		return outputCurrency.getRate_float() / usd.getRate_float();
	}

}
