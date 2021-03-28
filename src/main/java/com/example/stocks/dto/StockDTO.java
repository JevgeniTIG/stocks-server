package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockDTO {
	private Long id;
	private String ticker;
	private String currency;
	private String companyName;
	private String stockExchange;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private String companyInfo;
	private LocalDateTime createdDate;
}
