package com.kataalten.controller;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kataalten.auth.dto.RegisterRequest;
import com.kataalten.auth.model.AppRole;
import com.kataalten.auth.model.AppUser;
import com.kataalten.auth.repository.RoleRepository;
import com.kataalten.auth.repository.UserRepository;
import com.kataalten.auth.service.AuthService;
import com.kataalten.entities.Product;
import com.kataalten.repository.ProductRepository;
import com.kataalten.service.facade.CartService;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

	private final ProductRepository productRepository;
	private final RoleRepository roleRepository;
	private final AuthService authService;
	
	private final CartService cartService;
	private final UserRepository userRepository;
	
	private final Environment environment;

	

	
	public MyCommandLineRunner(ProductRepository productRepository, RoleRepository roleRepository,
			AuthService authService, CartService cartService, UserRepository userRepository, Environment environment) {
		super();
		this.productRepository = productRepository;
		this.roleRepository = roleRepository;
		this.authService = authService;
		this.cartService = cartService;
		this.userRepository = userRepository;
		this.environment = environment;
	}

	public void run(String... args) throws Exception {
		System.out.println("Active profiles: " + Arrays.toString(environment.getActiveProfiles()));
		DecimalFormat df = new DecimalFormat("00.00");
		if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {

			AppRole role2 = new AppRole();
			role2.setName("ADMIN");
			roleRepository.save(role2);

			AppRole role = new AppRole();
			role.setName("USER");
			roleRepository.save(role);

			Set<AppRole> userRoles = new HashSet<>();
			RegisterRequest request = new RegisterRequest();
			request.setEmail("admin@admin.com");
			request.setPassword("admin1234");
			userRoles.add(roleRepository.findById(1).get());
			request.setRoles(userRoles);
			authService.register(request);

			for (int i = 1; i < 10; i++) {
				Product product = new Product();
				product.setName(generateRandomString(4));
				product.setDescription(generateRandomString(10));
				productRepository.save(product);

			}
			AppUser appUser = userRepository.findById(1L).get();
			Product product = productRepository.findById(1L).get();
			cartService.addProductToCart(appUser, product, 1);
		}
	}

	public static String generateRandomString(int length) {
		// Define the characters you want to include in the random string
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}

		return sb.toString();
	}

}
