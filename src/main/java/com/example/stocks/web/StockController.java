package com.example.stocks.web;

import com.example.stocks.dto.StockDTO;
import com.example.stocks.facade.StockFacade;
import com.example.stocks.payload.response.MessageResponse;
import com.example.stocks.service.StockService;
import com.example.stocks.service.GetStockWikiDataService;
import com.example.stocks.service.TestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin
public class StockController {
	@Autowired
	private StockService stockService;
	@Autowired
	private StockFacade stockFacade;


	@PostMapping("/{ticker}/create")
	public ResponseEntity<MessageResponse> createStock(@PathVariable("ticker") String ticker) throws IOException {
		stockService.createStock(ticker);

		return new ResponseEntity<>(new MessageResponse("Stock created"), HttpStatus.OK);
	}

	@PostMapping("/{id}/delete")
	public ResponseEntity<MessageResponse> deleteStock(@PathVariable("id") Long id) {
		stockService.deleteStock(id);
		return new ResponseEntity<>(new MessageResponse("Stock deleted"), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<StockDTO>> getAllStocks() {
		List<StockDTO> stockDTOList = stockService.getAllStocks()
				.stream()
				.map(stockFacade::databaseStockToStockDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(stockDTOList, HttpStatus.OK);
	}



}
