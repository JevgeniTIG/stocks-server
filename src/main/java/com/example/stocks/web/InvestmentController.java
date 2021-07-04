package com.example.stocks.web;


import com.example.stocks.dto.InvestmentPortfolio;
import com.example.stocks.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investor")
@CrossOrigin
public class InvestmentController {

	@Autowired
	private InvestmentService investmentService;

	@PostMapping("/{amount}/get")
	public ResponseEntity<InvestmentPortfolio> getInvestorProfile(@PathVariable("amount") Integer investedAmount){
		InvestmentPortfolio portfolio = investmentService.getPortfolioTotalValue(investedAmount);

		return new ResponseEntity<>(portfolio, HttpStatus.OK);
	}

}
