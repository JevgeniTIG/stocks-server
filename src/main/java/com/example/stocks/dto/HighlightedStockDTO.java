package com.example.stocks.dto;

import com.example.stocks.entity.enums.EvaluationStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HighlightedStockDTO {
	private Long id;
	private String ticker;
	private BigDecimal dropInPercent;
	private BigDecimal minPrice;
	private EvaluationStatus status;
	private LocalDateTime createdDate;
}
