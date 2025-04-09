package com.kataalten.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kataalten.auth.model.AppUser;
import com.kataalten.entities.Cart;


public interface CartRepository extends JpaRepository<Cart, Long>{
	Optional<Cart> findByAppUser(AppUser user);

}
