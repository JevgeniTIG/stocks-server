package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioPosition {

	private String ticker;
	private BigDecimal profitability;

}
