package com.kataalten.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kataalten.entities.Product;
import com.kataalten.repository.ProductRepository;
import com.kataalten.service.facade.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product createProduct(Product product) {
		product.setCreatedAt(System.currentTimeMillis());
		product.setUpdatedAt(System.currentTimeMillis());
		return productRepository.save(product);
	}

	@Override
	public Optional<Product> updateProduct(Long id, Product product) {
		return productRepository.findById(id).map(existing -> {
			product.setId(id);
			product.setUpdatedAt(System.currentTimeMillis());
			product.setCreatedAt(existing.getCreatedAt());
			return productRepository.save(product);
		});
	}

	@Override
	public boolean deleteProduct(Long id) {
		return productRepository.findById(id).map(product -> {
			productRepository.delete(product);
			return true;
		}).orElse(false);
	}

}
