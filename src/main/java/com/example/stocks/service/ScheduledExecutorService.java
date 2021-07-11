package com.example.stocks.service;

import com.example.stocks.entity.HighlightedStock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledExecutorService {

	private PriceService priceService;
	private StockService stockService;
	private MailNotificationService mailNotificationService;

	public ScheduledExecutorService(PriceService priceService,
									StockService stockService,
									MailNotificationService mailNotificationService) {
		this.priceService = priceService;
		this.stockService = stockService;
		this.mailNotificationService = mailNotificationService;
	}


	@Scheduled(cron = "0 0 6 * * TUE,WED,THU,FRI,SAT")
	public void saveStocksPricesAsScheduled() {

		priceService.savePriceDaily();
		List<HighlightedStock> previousDayHighlightedStocksList = priceService.getAllActiveHighlightedStocks();

		priceService.makeOldHighlightedStocksInactive();
		List<HighlightedStock> highlightedStocksList = priceService.evaluateStockPrices();

		List<String> oldListTickers = new ArrayList<>();
		previousDayHighlightedStocksList.forEach(highlightedStock -> {
			oldListTickers.add(highlightedStock.getTicker());
		});

		highlightedStocksList.forEach(highlightedStock -> {
			if (!oldListTickers.contains(highlightedStock.getTicker())) {
				mailNotificationService.sendMail();
			}
		});

	}

	@Scheduled(cron = "0 0 12 L * ?")
	public void updateStockYearMinAndMaxValues() {
		if (stockService.updateStockYearMinAndMaxValues()) {
			mailNotificationService.sendMailMinMaxUpdated();
		}
		else if (!stockService.updateStockYearMinAndMaxValues()) {
			mailNotificationService.sendMailMinMaxUpdateFailed();
		}

	}


}
