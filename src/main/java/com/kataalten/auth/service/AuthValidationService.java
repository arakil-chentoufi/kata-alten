package com.kataalten.auth.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.kataalten.auth.dto.AuthValidationErrorResponse;
import com.kataalten.auth.dto.ValidationErrorDetails;

@Service
public class AuthValidationService {

	public AuthValidationErrorResponse authErrorHandler(BindingResult bindingResult) {

		List<ValidationErrorDetails> details = bindingResult.getFieldErrors().stream()
				.map(error -> new ValidationErrorDetails(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

		return AuthValidationErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message("Validation failed for the request body.").details(details).build();
	}

}
