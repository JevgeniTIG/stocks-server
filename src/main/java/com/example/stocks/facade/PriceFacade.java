package com.example.stocks.facade;

import com.example.stocks.dto.PriceDTO;
import com.example.stocks.entity.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceFacade {
	public PriceDTO priceToPriceDTO(Price price) {
		PriceDTO priceDTO = new PriceDTO();
		priceDTO.setId(price.getId());
		priceDTO.setPrice(price.getPrice());
		priceDTO.setCreatedDate(price.getCreatedDate());

		return priceDTO;
	}
}
