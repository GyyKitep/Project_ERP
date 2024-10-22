package br.com.erp_api.controller.dto;

import br.com.erp_api.model.user.User;
import lombok.Getter;

@Getter
public class UserDto {
	
	private Long id;
	private String email;

	public UserDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
	}	
	
}