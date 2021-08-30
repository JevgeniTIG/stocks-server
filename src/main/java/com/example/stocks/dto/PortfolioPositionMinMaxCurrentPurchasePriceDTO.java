package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioPositionMinMaxCurrentPurchasePriceDTO {
	private String ticker;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private BigDecimal currentValue;
	private BigDecimal purchasePrice;
}
