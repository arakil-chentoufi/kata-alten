package com.kataalten.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kataalten.auth.model.AppUser;
import com.kataalten.auth.repository.UserRepository;
import com.kataalten.entities.Cart;
import com.kataalten.entities.CartItem;
import com.kataalten.entities.Product;
import com.kataalten.repository.CartRepository;
import com.kataalten.service.facade.CartService;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;




	public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
		super();
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
	}

	public Cart getCartForUser(AppUser user) {
	    AppUser persistedUser = userRepository.findById(user.getId())
	        .orElseThrow(() -> new RuntimeException("User not found in database"));

	    return cartRepository.findByAppUser(persistedUser)
	        .orElseGet(() -> {
	            Cart newCart = new Cart();
	            newCart.setAppUser(persistedUser);
	            return cartRepository.save(newCart);
	        });
	}

	public Cart addProductToCart(AppUser user, Product product, int quantity) {
		Cart cart = getCartForUser(user);
		Optional<CartItem> existingItem = cart.getItems().stream()
				.filter(item -> item.getProduct().getId().equals(product.getId())).findFirst();

		if (existingItem.isPresent()) {
			existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
		} else {
			CartItem newItem = new CartItem();
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			cart.getItems().add(newItem);
		}

		return cartRepository.save(cart);
	}

}
