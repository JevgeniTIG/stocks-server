package com.example.stocks.web;

import com.example.stocks.payload.response.MessageResponse;
import com.example.stocks.service.MailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin
public class MailNotificationController {

	@Autowired
	private MailNotificationService mailNotificationService;


	@PostMapping("/send")
	public ResponseEntity<MessageResponse> sendMail(){
		mailNotificationService.sendMail();

		return new ResponseEntity<>(new MessageResponse("Mail sent"), HttpStatus.OK);
	}
}
