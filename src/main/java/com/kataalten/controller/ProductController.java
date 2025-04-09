package com.kataalten.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kataalten.entities.Product;
import com.kataalten.service.facade.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "products")
@CrossOrigin(origins = "*")
@Tag(name = "Product Management", description = "Operations related to products")
public class ProductController {

	private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

	@GetMapping
	@Operation(summary = "Get all products", description = "Returns a list of all available products.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

	@GetMapping("/{id}")
	@Operation(summary = "Get product by ID", description = "Returns a product by its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product found"),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
		return productService.getProductById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Create a new product", description = "Creates a new product (admin only).")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product created"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Only admin can create") })
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		if (!isAdmin()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	 @PatchMapping("/{id}")
	    @Operation(summary = "Update a product", description = "Updates a product by ID (admin only).")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Product updated"),
	        @ApiResponse(responseCode = "403", description = "Forbidden - Only admin can update"),
	        @ApiResponse(responseCode = "404", description = "Product not found")
	    })
	    public ResponseEntity<Product> updateProduct(
	            @PathVariable Long id, 
	            @Valid @RequestBody Product product) {
	        if (!isAdmin()) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	        }
	        return productService.updateProduct(id, product)
	                .map(ResponseEntity::ok)
	                .orElseGet(() -> ResponseEntity.notFound().build());
	    }


		@DeleteMapping("/{id}")
		@Operation(summary = "Delete a product", description = "Deletes a product by ID (admin only).")
		@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Product deleted"),
				@ApiResponse(responseCode = "403", description = "Forbidden - Only admin can delete"),
				@ApiResponse(responseCode = "404", description = "Product not found") })
		public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
			if (!isAdmin()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			return productService.deleteProduct(id) ? ResponseEntity.noContent().build()
					: ResponseEntity.notFound().build();
		}

    private boolean isAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email:"+ email);
        return "admin@admin.com".equals(email);
    }

}
