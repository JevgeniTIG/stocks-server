package com.example.stocks.repository;

import com.example.stocks.entity.HighlightedStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface HighlightedStockRepository extends JpaRepository<HighlightedStock, Long> {

	HighlightedStock findHighlightedStockByTicker(String ticker);

	HighlightedStock findHighlightedStockByTickerAndDropInPercent(String ticker, BigDecimal dropInPercent);


}
