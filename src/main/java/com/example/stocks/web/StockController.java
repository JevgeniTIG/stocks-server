package com.example.stocks.web;

import com.example.stocks.dto.StockDTO;
import com.example.stocks.facade.StockFacade;
import com.example.stocks.payload.response.MessageResponse;
import com.example.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<MessageResponse> createStock(@PathVariable("ticker") String ticker) {
		stockService.createStock(ticker);

		return new ResponseEntity<>(new MessageResponse("Stock created"), HttpStatus.OK);
	}


	@PostMapping("/{id}/delete")
	public ResponseEntity<MessageResponse> deleteStock(@PathVariable("id") Long id) {

		if (stockService.stockExists(id)) {
			stockService.deleteStock(id);
			return new ResponseEntity<>(new MessageResponse("Stock deleted"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new MessageResponse("Stock with id [" + id + "] doesn't exist" ), HttpStatus.BAD_REQUEST);
	}


	@GetMapping("/all")
	public ResponseEntity<List<StockDTO>> getAllStocks() {
		List<StockDTO> stockDTOList = stockService.getAllStocks()
				.stream()
				.map(stockFacade::databaseStockToStockDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(stockDTOList, HttpStatus.OK);
	}


	@PostMapping("/update-stock-info/{id}")
	public ResponseEntity<MessageResponse> updateStockInfo(@PathVariable("id") Long id,
														   @RequestParam("companyInfo") String companyInfo) {
		if (stockService.stockExists(id)) {
			stockService.updateStockInfo(id, companyInfo);
			return new ResponseEntity<>(new MessageResponse("Stock info updated"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new MessageResponse("No stock found with id [" + id + "]" ), HttpStatus.BAD_REQUEST);
	}



}
