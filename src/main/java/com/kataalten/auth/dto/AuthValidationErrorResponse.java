package com.kataalten.auth.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthValidationErrorResponse {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private List<ValidationErrorDetails> details;

}
