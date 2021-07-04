package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvestmentPortfolio {

	private BigDecimal totalValue;
	private BigDecimal initialInvestmentAmountTotal;
	private List<PortfolioPosition> portfolioPositions;
	private BigDecimal totalProfitability;
	private BigDecimal totalProfitabilityInPercent;

}
