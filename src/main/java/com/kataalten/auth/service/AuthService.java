package com.kataalten.auth.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kataalten.auth.dto.LoginRequest;
import com.kataalten.auth.dto.AuthenticationResponse;
import com.kataalten.auth.dto.RegisterRequest;
import com.kataalten.auth.dto.RegistrationResponse;
import com.kataalten.auth.model.AppRole;
import com.kataalten.auth.model.AppUser;
import com.kataalten.auth.repository.RoleRepository;
import com.kataalten.auth.repository.UserRepository;
import com.kataalten.auth.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userrepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtils;
	private final UserDetailsServiceImpl userDetailsService;

	public RegistrationResponse register(RegisterRequest request) {

		// Assuming you have a RoleService or similar to fetch roles from the database
		AppRole defaultRole = roleRepository.findByName("USER");

		Set<AppRole> userRoles = new HashSet<>();
		if (request.getRoles() == null || request.getRoles().isEmpty()) {
			userRoles.add(defaultRole);
		} else {
			userRoles.addAll(request.getRoles());
		}

		var user = AppUser.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.username(request.getEmail())
				.roles(userRoles)
				.build();

		var savedUser = userrepository.save(user);

		return RegistrationResponse.builder().statusCode("200")
				.message(savedUser.getUsername() + "Registered SuccessFully").build();
	}

	public AuthenticationResponse authenticate(LoginRequest request) throws Exception {

		System.out.println("hello authenticate service");

		// Validate user's credentials
		authenticateUser(request.getUsername(), request.getPassword());

		// Retrieve the user from the database
		AppUser user = fetchUserByUsername(request.getUsername());

		// Generate a JWT token for the authenticated user
		String jwt = JwtUtil.generateToken(user);

		// Return a successful authentication response
		return buildSuccessResponse(jwt);

	}

	private void authenticateUser(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}

	private AppUser fetchUserByUsername(String username) throws Exception {
		return userRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));
	}

	private AuthenticationResponse buildSuccessResponse(String jwt) {
		return AuthenticationResponse.builder().accessToken(jwt).message("Successfully Logged In").statusCode(200)
				.build();
	}

	private AuthenticationResponse buildErrorResponse(String errorMessage) {
		return AuthenticationResponse.builder().statusCode(500).error(errorMessage).build();
	}

	public boolean isTokenValide(String token) {

		// Remove the "Bearer " prefix from the token if it's present
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		} else {
			return false;
		}

		try {

			String Email = jwtUtils.extractUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(Email);

			return jwtUtils.isTokenValid(token, userDetails);

		} catch (Exception e) {
			return false;
		}

	}

}
