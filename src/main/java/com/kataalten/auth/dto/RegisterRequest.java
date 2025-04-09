package com.kataalten.auth.dto;

import java.util.Set;

import com.kataalten.auth.model.AppRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotNull(message = "Username should not be null")
	@NotEmpty(message = "Username is Empty")
	private String firstname;

	@NotNull(message = "lastname should not be null")
	@NotEmpty(message = "lastname is Empty")
	private String lastname;

	@Email(message = "Email is not Valide")
	@NotEmpty(message = "Email is Empty")
	private String email;

	@Size(min = 6, message = "Password must be at least 6 characters")
	@NotEmpty(message = "password is empty")
	private String password;

	private Set<AppRole> roles;

}
