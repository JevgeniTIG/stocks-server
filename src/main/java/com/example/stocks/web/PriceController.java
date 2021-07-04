package com.example.stocks.web;

import com.example.stocks.dto.HighlightedStockDTO;
import com.example.stocks.dto.PriceDTO;
import com.example.stocks.facade.HighlightedStockFacade;
import com.example.stocks.facade.PriceFacade;
import com.example.stocks.payload.response.MessageResponse;
import com.example.stocks.service.PriceService;
import com.example.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/price")
@CrossOrigin
public class PriceController {
	@Autowired
	private PriceService priceService;
	@Autowired
	private StockService stockService;
	@Autowired
	private PriceFacade priceFacade;
	@Autowired
	private HighlightedStockFacade highlightedStockFacade;

	@PostMapping("/save")
	public ResponseEntity<MessageResponse> savePrices(){
		priceService.savePriceDaily();

		return new ResponseEntity<>(new MessageResponse("Price saved"), HttpStatus.OK);
	}


//	@PostMapping("/evaluate")
//	public ResponseEntity<List<HighlightedStockDTO>> evaluateStockPrices(){
//		List<HighlightedStockDTO> highlightedStockDTOList = priceService.evaluateStockPrices()
//				.stream()
//				.map(highlightedStockFacade::highlightedStockToHighlightedStockDTO)
//				.collect(Collectors.toList());
//
//		return new ResponseEntity<>(highlightedStockDTOList, HttpStatus.OK);
//	}


	@PostMapping("/evaluate")
	public ResponseEntity<List<HighlightedStockDTO>> evaluateStockPrices(){
		List<HighlightedStockDTO> highlightedStockDTOList = priceService.getAllActiveHighlightedStocks()
				.stream()
				.map(highlightedStockFacade::highlightedStockToHighlightedStockDTO)
				.collect(Collectors.toList());

		return new ResponseEntity<>(highlightedStockDTOList, HttpStatus.OK);
	}


	@GetMapping("/{id}/all")
	public ResponseEntity<List<PriceDTO>> getStockPrices(@PathVariable("id") Long id) {
		if (stockService.stockExists(id)) {
			List<PriceDTO> priceDTOList = priceService.getStockPrices(id)
					.stream()
					.map(priceFacade::priceToPriceDTO)
					.collect(Collectors.toList());
			return new ResponseEntity<>(priceDTOList, HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}


}
