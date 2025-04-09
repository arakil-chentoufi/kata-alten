package com.kataalten.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Cart;
import com.kataalten.entities.Product;
import com.kataalten.repository.ProductRepository;
import com.kataalten.service.facade.CartService;
import com.kataalten.utils.AddToCartRequest;


@RestController
@RequestMapping(value = "cart")
@CrossOrigin(origins = "*")
public class CartController {

  
    private final CartService cartService;

    private final ProductRepository productRepository;
    
    

    public CartController(CartService cartService, ProductRepository productRepository) {
		super();
		this.cartService = cartService;
		this.productRepository = productRepository;
	}

	@PostMapping
    public Cart getCart(@RequestBody AppUser user) {
        return cartService.getCartForUser(user);
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody AddToCartRequest cartRequest ) {
        Product product = productRepository.findById(cartRequest.getProductId()).orElseThrow();
        return cartService.addProductToCart(cartRequest.getUser(), product, cartRequest.getQuantity());
    }

   
}

