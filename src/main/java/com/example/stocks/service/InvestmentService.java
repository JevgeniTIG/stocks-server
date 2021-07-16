package com.example.stocks.service;

import com.example.stocks.dto.InvestmentPortfolioDTO;
import com.example.stocks.dto.PortfolioPositionDTO;
import com.example.stocks.entity.Investment;
import com.example.stocks.repository.InvestmentRepository;
import com.example.stocks.repository.PriceRepository;
import com.example.stocks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvestmentService {

	public static final Logger LOG = LoggerFactory.getLogger(InvestmentService.class);

	private final InvestmentRepository investmentRepository;
	private final PriceRepository priceRepository;
	private final StockRepository stockRepository;

	@Autowired
	public InvestmentService(
			InvestmentRepository investmentRepository,
			PriceRepository priceRepository,
			StockRepository stockRepository) {
		this.investmentRepository = investmentRepository;
		this.priceRepository = priceRepository;
		this.stockRepository = stockRepository;
	}


	public List<Investment> getAllInvestments() {
		List<Investment> availableInvestments = new ArrayList<>();
		try {
			availableInvestments = investmentRepository.findAllBy();
		} catch (Exception ex) {
			LOG.info("Unexpected exception while loading investments from database ", ex);
		}
		return availableInvestments;
	}


	public InvestmentPortfolioDTO getPortfolioTotalValue(Integer investedAmount) {
		InvestmentPortfolioDTO portfolio = new InvestmentPortfolioDTO();

		List<PortfolioPositionDTO> portfolioPositions = new ArrayList<>();
		portfolio.setTotalValue(BigDecimal.ZERO);
		portfolio.setTotalProfitability(BigDecimal.ZERO);
		portfolio.setInitialInvestmentAmountTotal(BigDecimal.ZERO);

		getAllInvestments().forEach(investment -> {

			PortfolioPositionDTO portfolioPosition = new PortfolioPositionDTO();

			Long stockId = stockRepository.findDatabaseStockByTicker(investment.getTicker()).getId();
			int index = priceRepository.findAllByDatabaseStockIdOrderByCreatedDateAsc(stockId).size();
			BigDecimal latestPrice = priceRepository.findAllByDatabaseStockIdOrderByCreatedDateAsc(stockId).get(index - 1).getPrice();
			BigDecimal currentValue;

			if (investment.getCurrency().equals("EUR")) {
				currentValue = BigDecimal.valueOf(investedAmount).divide(investment.getPurchasePrice(), 2, RoundingMode.FLOOR).multiply(latestPrice);
				portfolio.setTotalValue(portfolio.getTotalValue().add(currentValue));
				portfolioPosition.setTicker(investment.getTicker());
				portfolioPosition.setProfitability(currentValue.subtract(BigDecimal.valueOf(investedAmount)));

				portfolioPositions.add(portfolioPosition);

				portfolio.setPortfolioPositions((portfolioPositions));
				portfolio.setTotalProfitability(portfolio.getTotalProfitability().add(portfolioPosition.getProfitability()));
				portfolio.setTotalProfitabilityInPercent(portfolio.getTotalProfitability().multiply(BigDecimal.valueOf(100)).divide(portfolio.getTotalValue().subtract(portfolio.getTotalProfitability()), 2, RoundingMode.FLOOR));
				portfolio.setInitialInvestmentAmountTotal(portfolio.getInitialInvestmentAmountTotal().add(BigDecimal.valueOf(investedAmount)));
			} else if (investment.getCurrency().equals("USD")) {
				BigDecimal eurUsd = BigDecimal.valueOf(1.19);
				BigDecimal usdEur = BigDecimal.valueOf(0.84);

//				try {
//					eurUsd = YahooFinance.getFx("EURUSD=X").getPrice();
//					LOG.info("eurusd" + eurUsd);
//					usdEur = YahooFinance.getFx("USDEUR=X").getPrice();
//					LOG.info("usdeur" + usdEur);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				calculateInvestmentPortfolio(investedAmount, portfolio, portfolioPositions, investment, portfolioPosition, latestPrice, eurUsd, usdEur);
			} else if (investment.getCurrency().equals("SEK")) {
				BigDecimal eurSek = BigDecimal.valueOf(10);
				BigDecimal sekEur = BigDecimal.valueOf(0.098);
				calculateInvestmentPortfolio(investedAmount, portfolio, portfolioPositions, investment, portfolioPosition, latestPrice, eurSek, sekEur);
			}

		});

		return portfolio;
	}

	public void calculateInvestmentPortfolio(Integer investedAmount, InvestmentPortfolioDTO portfolio, List<PortfolioPositionDTO> portfolioPositions, Investment investment, PortfolioPositionDTO portfolioPosition, BigDecimal latestPrice, BigDecimal eurSek, BigDecimal sekEur) {
		BigDecimal currentValue;
		currentValue = BigDecimal.valueOf(investedAmount).multiply(eurSek).divide(investment.getPurchasePrice(), 2, RoundingMode.FLOOR).multiply(latestPrice).multiply(sekEur);
		portfolio.setTotalValue(portfolio.getTotalValue().add(currentValue));
		portfolioPosition.setTicker(investment.getTicker());
		portfolioPosition.setProfitability(currentValue.subtract(BigDecimal.valueOf(investedAmount)));
		portfolioPositions.add(portfolioPosition);
		portfolio.setPortfolioPositions(portfolioPositions);
		portfolio.setTotalProfitability(portfolio.getTotalProfitability().add(portfolioPosition.getProfitability()));
		portfolio.setTotalProfitabilityInPercent(portfolio.getTotalProfitability().multiply(BigDecimal.valueOf(100)).divide(portfolio.getTotalValue().subtract(portfolio.getTotalProfitability()), 2, RoundingMode.FLOOR));
		portfolio.setInitialInvestmentAmountTotal(portfolio.getInitialInvestmentAmountTotal().add(BigDecimal.valueOf(investedAmount)));
	}


	public boolean stockIsPurchased(String ticker) {
		return investmentRepository.findInvestmentByTicker(ticker) != null;
	}

	public void purchaseStock(String ticker, BigDecimal purchasePrice, String currency) {
		Investment newInvestment = new Investment();
		newInvestment.setTicker(ticker);
		newInvestment.setPurchasePrice(purchasePrice);
		newInvestment.setCurrency(currency);

		investmentRepository.save(newInvestment);
	}
}
