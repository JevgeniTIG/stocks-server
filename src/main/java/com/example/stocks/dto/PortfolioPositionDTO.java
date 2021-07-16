package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioPositionDTO {

	private String ticker;
	private BigDecimal profitability;

}
