package com.example.stocks.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Investment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String ticker;

	@Column(nullable = false)
	private BigDecimal purchasePrice;

	@Column(nullable = false)
	private String currency;

	@JsonFormat(pattern = "yyyy-mm-dd HH-mm-ss")
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@PrePersist
	protected void onCreate() {this.createdDate = LocalDateTime.now();}
}
