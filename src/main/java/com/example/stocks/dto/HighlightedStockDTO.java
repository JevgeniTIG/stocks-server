package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HighlightedStockDTO {
	private Long id;
	private String ticker;
	private BigDecimal dropInPercent;
	private LocalDateTime createdDate;
}
