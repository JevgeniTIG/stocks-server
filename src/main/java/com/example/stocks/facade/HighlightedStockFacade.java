package com.example.stocks.facade;

import com.example.stocks.dto.HighlightedStockDTO;
import com.example.stocks.entity.HighlightedStock;
import org.springframework.stereotype.Component;

@Component
public class HighlightedStockFacade {
	public HighlightedStockDTO highlightedStockToHighlightedStockDTO(HighlightedStock highlightedStock) {
		HighlightedStockDTO stockDTO = new HighlightedStockDTO();
		stockDTO.setId(highlightedStock.getId());
		stockDTO.setTicker(highlightedStock.getTicker());
		stockDTO.setDropInPercent(highlightedStock.getDropInPercent());
		stockDTO.setMinPrice(highlightedStock.getMinPrice());
		stockDTO.setStatus(highlightedStock.getStatus());
		stockDTO.setCreatedDate(highlightedStock.getCreatedDate());


		return stockDTO;
	}
}
