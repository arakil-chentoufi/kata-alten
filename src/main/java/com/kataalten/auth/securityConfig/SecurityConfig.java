package com.kataalten.auth.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.kataalten.auth.filters.JWTAuthFilter;
import com.kataalten.auth.service.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetails;
	private final JWTAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(
	                "/swagger-ui/**", 
	                "/swagger-ui.html", 
	                "/swagger-resources/**", 
	                "/v3/api-docs/**", 
	                "/configuration/**", 
	                "/webjars/**",
	                "/api-auth/**" 
	            ).permitAll() 
	            .requestMatchers("/products/**").hasAuthority("ADMIN") 
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Session sans état
	        .authenticationProvider(authenticationProvider()) // Utilisation de votre provider d'authentification
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Ajoute le filtre JWT avant le filtre d'authentification
	        .exceptionHandling(exception -> exception.authenticationEntryPoint((request, response, authException) -> {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Please log in");
	        }));
	    
	    return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetails);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
