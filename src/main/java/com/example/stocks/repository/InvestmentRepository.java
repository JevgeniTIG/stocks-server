package com.example.stocks.repository;

import com.example.stocks.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, String> {

	Investment findInvestmentByTicker(String ticker);

	List<Investment> findAllBy();
}
