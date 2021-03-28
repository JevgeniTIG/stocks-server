package com.example.stocks.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = true)
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	private DatabaseStock databaseStock;

	@Column(updatable = false)
	private LocalDateTime createdDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
	}


}
