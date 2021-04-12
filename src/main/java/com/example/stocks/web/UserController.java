package com.example.stocks.web;

import com.example.stocks.dto.UserDTO;
import com.example.stocks.entity.User;
import com.example.stocks.facade.UserFacade;
import com.example.stocks.service.UserService;
import com.example.stocks.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private ResponseErrorValidator responseErrorValidator;

	@GetMapping("/")
	public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
		User user = userService.getCurrentUser(principal);
		UserDTO userDTO = userFacade.userToUserDTO(user);

		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}


	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
		User user = userService.getUserById(Long.parseLong(userId));
		UserDTO userDTO = userFacade.userToUserDTO(user);

		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}


	@PostMapping("/update")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
		ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
		if (!ObjectUtils.isEmpty(errors)) return errors;

		User user = userService.updateUser(userDTO, principal);
		UserDTO userUpdated = userFacade.userToUserDTO(user);

		return new ResponseEntity<>(userUpdated, HttpStatus.OK);
	}

}
