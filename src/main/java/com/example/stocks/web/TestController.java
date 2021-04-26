package com.example.stocks.web;

import com.example.stocks.service.TestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/test")
@CrossOrigin
public class TestController {

	@Autowired
	private TestingService testingService;


	@PostMapping("/create-hs")
	public void createHS(@RequestParam("ticker") String ticker,
						 @RequestParam("dropInPercent") BigDecimal dropInPercent){
		testingService.createHS(ticker, dropInPercent);
	}
}
