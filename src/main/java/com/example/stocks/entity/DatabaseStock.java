package com.example.stocks.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DatabaseStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String ticker;

	@Column(nullable = false)
	private String currency;

	@Column(nullable = true)
	private String companyName;

	@Column(nullable = true)
	private String stockExchange;

	@Column(nullable = false)
	private BigDecimal minPrice;

	@Column(nullable = false)
	private BigDecimal maxPrice;

	@Column(nullable = true, length = 1000)
	private String companyInfo;


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "databaseStock", orphanRemoval = true)
	private List<Price> prices;

	{
		prices = new ArrayList<>();
	}

	@JsonFormat(pattern = "yyyy-mm-dd HH-mm-ss")
	@Column(updatable = false)
	private LocalDateTime createdDate;



	@PrePersist
	protected void onCreate() {this.createdDate = LocalDateTime.now();}

}
