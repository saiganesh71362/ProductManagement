package com.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.model.Product;
import com.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
    // only single constructor @Autowired No need Spring Ioc Container managed
	// Dependency Injection
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/createProduct")
	public ResponseEntity<String> createProduct(@RequestBody Product product) {
		String reponse = productService.createProduct(product);
		return new ResponseEntity<String>(reponse, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) throws Exception {
		Product productById = productService.getProductById(id);
		return new ResponseEntity<Product>(productById, HttpStatus.OK);
	}

	@GetMapping("/allProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> allProducts = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id) {
		String deleteProductById = productService.deleteProductById(id);
		return new ResponseEntity<String>(deleteProductById, HttpStatus.OK);

	}

	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		Product updateProductsById = productService.updateProductById(product, id);

		return new ResponseEntity<Product>(updateProductsById, HttpStatus.OK);

	}

}
