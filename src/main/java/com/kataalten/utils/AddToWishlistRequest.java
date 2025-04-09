package com.kataalten.utils;

import com.kataalten.auth.model.AppUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToWishlistRequest {
	private AppUser user;
	private Long productId;

}
