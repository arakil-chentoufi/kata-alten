package com.kataalten.service.impl;

import org.springframework.stereotype.Service;

import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Product;
import com.kataalten.entities.Wishlist;
import com.kataalten.repository.WishlistRepository;
import com.kataalten.service.facade.WishlistService;

@Service
public class WishlistServiceImpl  implements WishlistService{
    private final WishlistRepository wishlistRepository;
    
    public WishlistServiceImpl(WishlistRepository wishlistRepository) {
		super();
		this.wishlistRepository = wishlistRepository;
	}

	public Wishlist getWishlistForUser(AppUser user) {
        return wishlistRepository.findByAppUser(user).orElseGet(() -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setAppUser(user);
            return wishlistRepository.save(wishlist);
        });
    }

    public Wishlist addProductToWishlist(AppUser user, Product product) {
        Wishlist wishlist = getWishlistForUser(user);
        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
        }
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProductFromWishlist(AppUser user, Product product) {
        Wishlist wishlist = getWishlistForUser(user);
        wishlist.getProducts().remove(product);
        return wishlistRepository.save(wishlist);
    }
 
}
