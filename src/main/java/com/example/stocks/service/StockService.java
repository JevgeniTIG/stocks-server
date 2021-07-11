package com.example.stocks.service;

import com.example.stocks.dto.StockDTO;
import com.example.stocks.entity.DatabaseStock;
import com.example.stocks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

	public static final Logger LOG = LoggerFactory.getLogger(StockService.class);

	private final StockRepository stockRepository;

	@Autowired
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public DatabaseStock createStock(String ticker) {
		if (ticker == null) return null;

		Stock yahooFinanceStock;

		try {
			yahooFinanceStock = YahooFinance.get(ticker);
			DatabaseStock databaseStock = new DatabaseStock();
			databaseStock.setTicker(yahooFinanceStock.getSymbol());
			databaseStock.setCompanyName(yahooFinanceStock.getName());
			databaseStock.setStockExchange(yahooFinanceStock.getStockExchange());
			databaseStock.setCurrency(yahooFinanceStock.getCurrency());
			databaseStock.setMinPrice(YahooFinance.get(ticker).getQuote().getYearLow());
			databaseStock.setMaxPrice(YahooFinance.get(ticker).getQuote().getYearHigh());
			databaseStock.setCompanyInfo("No info yet");
			return stockRepository.save(databaseStock);

		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("No stock found with ticker: " + ticker);
		}
		return null;
	}


	public void deleteStock(Long id) {
		stockRepository.deleteById(id);
	}


	public List<DatabaseStock> getAllStocks() {
		List<DatabaseStock> availableStocks = new ArrayList<>();
		try {
			availableStocks = stockRepository.findAllByOrderByCreatedDateDesc();
		} catch (Exception ex) {
			LOG.info("Unexpected exception while loading stocks from database ", ex);
		}
		return availableStocks;
	}

	public DatabaseStock getDatabaseStockById(Long id) {
		return stockRepository.findDatabaseStockById(id).get();
	}

	public DatabaseStock updateStockInfo(Long id, StockDTO stock) {

		DatabaseStock updateStockInfo = getDatabaseStockById(id);
		updateStockInfo.setCompanyInfo(stock.getCompanyInfo());

		return stockRepository.save(updateStockInfo);

	}

	public boolean updateStockYearMinAndMaxValues() {

		List<DatabaseStock> availableStocks = getAllStocks();
		try {
			for (DatabaseStock stock : availableStocks) {
				stock.setMinPrice(YahooFinance.get(stock.getTicker()).getQuote().getYearLow());
				stock.setMaxPrice(YahooFinance.get(stock.getTicker()).getQuote().getYearHigh());
				stockRepository.save(stock);
				LOG.info("Saving year min and high prices");

			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("Something went wrong with saving info for stock.");
			return false;
		}
	}


	public boolean stockExists(Long id) {
		return stockRepository.existsById(id);
	}

	public DatabaseStock getDatabaseStockByTicker(String ticker) {
		return stockRepository.findDatabaseStockByTicker(ticker);
	}


}
