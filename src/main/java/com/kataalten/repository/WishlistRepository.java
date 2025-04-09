package com.kataalten.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
	Optional<Wishlist> findByAppUser(AppUser user);

}
