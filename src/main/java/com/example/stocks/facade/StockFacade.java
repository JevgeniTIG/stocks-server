package com.example.stocks.facade;

import com.example.stocks.dto.StockDTO;
import com.example.stocks.entity.DatabaseStock;
import org.springframework.stereotype.Component;

@Component
public class StockFacade {
	public StockDTO databaseStockToStockDTO(DatabaseStock databaseStock) {
		StockDTO stockDTO = new StockDTO();
		stockDTO.setId(databaseStock.getId());
		stockDTO.setTicker(databaseStock.getTicker());
		stockDTO.setCurrency(databaseStock.getCurrency());
		stockDTO.setCompanyName(databaseStock.getCompanyName());
		stockDTO.setStockExchange(databaseStock.getStockExchange());
		stockDTO.setMinPrice(databaseStock.getMinPrice());
		stockDTO.setMaxPrice(databaseStock.getMaxPrice());
		stockDTO.setCompanyInfo(databaseStock.getCompanyInfo());
		stockDTO.setCreatedDate(databaseStock.getCreatedDate());


		return stockDTO;
	}
}
