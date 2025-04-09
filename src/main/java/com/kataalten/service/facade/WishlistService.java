package com.kataalten.service.facade;

import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Product;
import com.kataalten.entities.Wishlist;

public interface WishlistService {
	
	Wishlist getWishlistForUser(AppUser user);
	Wishlist addProductToWishlist(AppUser user, Product product);

	Wishlist removeProductFromWishlist(AppUser user, Product product);

}
