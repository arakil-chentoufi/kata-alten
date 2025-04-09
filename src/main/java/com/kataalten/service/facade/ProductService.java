package com.kataalten.service.facade;

import java.util.List;
import java.util.Optional;

import com.kataalten.entities.Product;

public interface ProductService {

	List<Product> getAllProducts();

	Optional<Product> getProductById(Long id);

	Product createProduct(Product product);

	Optional<Product> updateProduct(Long id, Product product);

	boolean deleteProduct(Long id);

}
