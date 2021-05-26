package com.example.stocks.service;

import com.example.stocks.entity.HighlightedStock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledExecutorService {

	private PriceService priceService;
	private MailNotificationService mailNotificationService;

	public ScheduledExecutorService(PriceService priceService,
									MailNotificationService mailNotificationService){
		this.priceService = priceService;
		this.mailNotificationService = mailNotificationService;
	}



	@Scheduled(cron = "0 0 8 * * TUE,WED,THU,FRI,SAT")
	public void saveStocksPricesAsScheduled(){

		priceService.savePriceDaily();

		List<HighlightedStock> highlightedStocksList = priceService.evaluateStockPrices();
		if (highlightedStocksList.size() > 0) {
			mailNotificationService.sendMail();
		}
	}




}
