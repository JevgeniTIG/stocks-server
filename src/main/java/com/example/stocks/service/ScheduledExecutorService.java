package com.example.stocks.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledExecutorService {

	private PriceService priceService;

	public ScheduledExecutorService(PriceService priceService){
		this.priceService = priceService;
	}



	@Scheduled(cron = "0 0 6 * * TUE,WED,THU,FRI,SAT")
	public void saveStocksPricesAsScheduled(){
		priceService.savePriceDaily();
	}



}
