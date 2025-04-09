package com.kataalten.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kataalten.auth.dto.LoginRequest;
import com.kataalten.auth.dto.RegisterRequest;
import com.kataalten.auth.service.AuthService;
import com.kataalten.auth.service.AuthValidationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api-auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final AuthValidationService authValidationService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Return an error messages
			return ResponseEntity.badRequest().body(authValidationService.authErrorHandler(bindingResult));
		}
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Return an error messages
			return ResponseEntity.badRequest().body(authValidationService.authErrorHandler(bindingResult));
		}
		try {
			return ResponseEntity.ok(authService.authenticate(request));

		} catch (Exception e) {
			// Handle other exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
		}
	}

	@GetMapping("/isTokenValide")
	public boolean isTokenExist(@RequestHeader("Authorization") String token) {

		return authService.isTokenValide(token);

	}
}
