package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioPositionMinMaxCurrentDTO {
	private String ticker;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private BigDecimal currentValue;
}
