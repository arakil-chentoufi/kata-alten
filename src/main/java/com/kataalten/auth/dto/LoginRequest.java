package com.kataalten.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@Email(message = "Username is not Valide")
	@NotEmpty(message = "Username is Empty")
	private String username;

	@Size(min = 6, message = "Password must be at least 6 characters")
	@NotEmpty(message = "password is Empty")
	private String password;

}
