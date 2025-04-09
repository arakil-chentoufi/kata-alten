package com.kataalten.auth.util;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kataalten.auth.model.AppUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil {

	private static SecretKey Key;
	private static final long EXPIRATION_TIME = 86400000; // 24 hours

	public JwtUtil() {
		String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
		byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
		JwtUtil.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
	}

	public static String generateToken(AppUser user) {
		return Jwts.builder().setSubject(user.getUsername()).claim("role", user.getRoles()) // Add the role to the token
																							// as a claim
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(Key).compact();
	}

	public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(Key).compact();
	}

	public static String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private static <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private static Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		System.out.println(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}

}
