package com.example.stocks.service;

import com.example.stocks.entity.HighlightedStock;
import com.example.stocks.repository.HighlightedStockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TestingService {

	private final HighlightedStockRepository highlightedStockRepository;

	public TestingService(HighlightedStockRepository highlightedStockRepository){
		this.highlightedStockRepository = highlightedStockRepository;
	}




	public void createHS(String ticker, BigDecimal dropInPercent){
		HighlightedStock hs = new HighlightedStock();
		hs.setTicker(ticker);
		hs.setDropInPercent(dropInPercent);
		highlightedStockRepository.save(hs);
	}
}
