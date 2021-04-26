package com.example.stocks.service;

import com.example.stocks.entity.DatabaseStock;
import com.example.stocks.entity.HighlightedStock;
import com.example.stocks.entity.Price;
import com.example.stocks.repository.PriceRepository;
import com.example.stocks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {
	public static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

	private final StockRepository stockRepository;
	private final PriceRepository priceRepository;


	@Autowired
	public PriceService(StockRepository stockRepository,
						PriceRepository priceRepository) {
		this.stockRepository = stockRepository;
		this.priceRepository = priceRepository;

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


	public List<Price> getStockListings(DatabaseStock stock) {
		List<Price> availablePrices = new ArrayList<>();
		try {
			availablePrices = priceRepository.findAllByDatabaseStockOrderByCreatedDateAsc(stock);
		} catch (Exception ex) {
			LOG.info("Unexpected exception while loading prices from database ", ex);
		}
		return availablePrices;
	}


	public void savePriceDaily() {

		List<DatabaseStock> availableStocks = getAllStocks();

		availableStocks.forEach(stock -> {

			List<Price> availablePrices = getStockListings(stock);

			Price stockPrice = new Price();

			if (availablePrices.size() > 60) {

				try {
					BigDecimal currentPrice = YahooFinance.get(stock.getTicker()).getQuote().getPreviousClose();
					stockPrice.setPrice(currentPrice);
					stockPrice.setDatabaseStock(stock);
					priceRepository.save(stockPrice);
					int smallestId = availablePrices.size() - 1;
					Long idToDelete = availablePrices.get(smallestId).getId();
					Price price = priceRepository.findPriceById(idToDelete);
					priceRepository.delete(price);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (availablePrices.size() < 60) {
				try {
					BigDecimal currentPrice = YahooFinance.get(stock.getTicker()).getQuote().getPreviousClose();
					stockPrice.setPrice(currentPrice);
					stockPrice.setDatabaseStock(stock);
					priceRepository.save(stockPrice);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public List<HighlightedStock> evaluateStockPrices() {

		List<DatabaseStock> availableStocks = getAllStocks();
		List<HighlightedStock> highlightedStocks = new ArrayList<>();

		if (!availableStocks.isEmpty()) {

			availableStocks.forEach(stock -> {

				if (!getStockListings(stock).isEmpty()) {

					List<Price> availablePrices = getStockListings(stock);

					BigDecimal currentMaxPrice = availablePrices.stream().max(Comparator.comparing(Price::getPrice)).get().getPrice();
					Long currentMaxPriceId = availablePrices.stream().max(Comparator.comparing(Price::getPrice)).get().getId();

					BigDecimal currentMinPrice = availablePrices.stream().min(Comparator.comparing(Price::getPrice)).get().getPrice();
					Long currentMinPriceId = availablePrices.stream().min(Comparator.comparing(Price::getPrice)).get().getId();

					final BigDecimal ONE_HUNDRED = new BigDecimal(100);
					final BigDecimal EIGHTY = new BigDecimal(80);
					BigDecimal minMultiplyHundred = currentMinPrice.multiply(ONE_HUNDRED);
					BigDecimal minToMaxInPercent = minMultiplyHundred.divide(currentMaxPrice, 3, RoundingMode.CEILING);

					BigDecimal dropInPercent = ONE_HUNDRED.subtract(minToMaxInPercent);

					if ((minToMaxInPercent.compareTo(EIGHTY) < 0) && (currentMinPriceId > currentMaxPriceId)) {

						HighlightedStock highlightedStock = new HighlightedStock();
						highlightedStock.setTicker(stock.getTicker());
						highlightedStock.setDropInPercent(dropInPercent);
						highlightedStocks.add(highlightedStock);
					}
				}
			});

			return highlightedStocks;
		}
		return new ArrayList<>();
	}


	public List<Price> getStockPrices(Long id) {
		List<Price> availablePrices = new ArrayList<>();
		try {
			availablePrices = priceRepository.findAllByDatabaseStockIdOrderByCreatedDateAsc(id);
		} catch (Exception ex) {
			LOG.info("Unexpected exception while loading prices from database ", ex);
		}
		return availablePrices;
	}


}
