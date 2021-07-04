package com.example.stocks.repository;

import com.example.stocks.entity.HighlightedStock;
import com.example.stocks.entity.enums.EvaluationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HighlightedStockRepository extends JpaRepository<HighlightedStock, Long> {

	HighlightedStock findHighlightedStockByTicker(String ticker);

	HighlightedStock findHighlightedStockByTickerAndDropInPercent(String ticker, BigDecimal dropInPercent);

	List<HighlightedStock> findAllByStatus(EvaluationStatus status);
}
