package com.kataalten.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kataalten.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
