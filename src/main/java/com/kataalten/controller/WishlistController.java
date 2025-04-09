package com.kataalten.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Product;
import com.kataalten.entities.Wishlist;
import com.kataalten.repository.ProductRepository;
import com.kataalten.service.facade.WishlistService;
import com.kataalten.utils.AddToWishlistRequest;



@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    
    private final WishlistService wishlistService;
   
	private final ProductRepository productRepository;
	
	  public WishlistController(WishlistService wishlistService, ProductRepository productRepository) {
			super();
			this.wishlistService = wishlistService;
			this.productRepository = productRepository;
		}

    @GetMapping
    public Wishlist getWishlist(@RequestBody AppUser user) {
        return wishlistService.getWishlistForUser(user);
    }

    @PostMapping("/add")
    public Wishlist addToWishlist(@RequestBody AddToWishlistRequest wishlistRequest ) {
        Product product = productRepository.findById(wishlistRequest.getProductId()).orElseThrow();
        return wishlistService.addProductToWishlist(wishlistRequest.getUser(), product);
    }

	@DeleteMapping("/remove")
	public Wishlist removeFromWishlist(@RequestBody AddToWishlistRequest wishlistRequest) {
		Product product = productRepository.findById(wishlistRequest.getProductId()).orElseThrow();
		return wishlistService.removeProductFromWishlist(wishlistRequest.getUser(), product);
	}
}
