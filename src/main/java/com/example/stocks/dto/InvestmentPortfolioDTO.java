package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvestmentPortfolioDTO {

	private BigDecimal totalValue;
	private BigDecimal initialInvestmentAmountTotal;
	private List<PortfolioPositionDTO> portfolioPositions;
	private BigDecimal totalProfitability;
	private BigDecimal totalProfitabilityInPercent;

}
