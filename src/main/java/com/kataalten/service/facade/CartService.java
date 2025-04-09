package com.kataalten.service.facade;

import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Cart;
import com.kataalten.entities.Product;

public interface CartService {
	Cart getCartForUser(AppUser user);
	Cart addProductToCart(AppUser user, Product product, int quantity);

}
