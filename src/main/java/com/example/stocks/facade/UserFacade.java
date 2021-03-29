package com.example.stocks.facade;

import com.example.stocks.dto.UserDTO;
import com.example.stocks.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
	public UserDTO userToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setUserName(user.getUsername());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}
}
