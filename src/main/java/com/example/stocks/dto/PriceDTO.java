package com.example.stocks.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceDTO {
	private Long id;
	private BigDecimal price;
	private LocalDateTime createdDate;
}
