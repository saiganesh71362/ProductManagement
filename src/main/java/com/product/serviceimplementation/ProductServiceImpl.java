package com.product.serviceimplementation;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.product.appconstants.AppConstants;
import com.product.model.Category;
import com.product.model.Product;
import com.product.repository.CategoryRepository;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
    // only single constructor @Autowired No need Spring Ioc Container managed
	// Dependency Injection
	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Transactional
	@Override
	public String createProduct(Product product) {
		Category category = product.getCategory();
		if (category != null && category.getId() != null) {
			category = categoryRepository.findById(category.getId()).orElse(null);
			if (category == null) {
				return "Category with ID " + product.getCategory().getId() + " not found";
			}
			product.setCategory(category);
		}

		Product savedProduct = productRepository.save(product);

		if (savedProduct != null && savedProduct.getId() != null) {
			return "Product added successfully with ID: " + savedProduct.getId();
		} else {
			return "Failed to add product";
		}
	}

	@Override
	public Product getProductById(Long id) throws Exception {
		Optional<Product> byId = productRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
		} else {
			throw new Exception(AppConstants.PRODUCT_ID_NOT_FOUND + id); // custom exception
		}

	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> all = productRepository.findAll();
		return all;
	}

	@Override
	public Product updateProductById(Product product, Long id) {
		Product existProduct = productRepository.findById(id).orElse(null);
		if (existProduct != null) {
			existProduct.setName(product.getName());
			existProduct.setDescription(product.getDescription());
			existProduct.setPrice(product.getPrice());
			existProduct.setCategory(product.getCategory());

			return productRepository.save(existProduct);
		} else {
			return null;
		}
	}

	@Override
	public String deleteProductById(Long id) {

		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);

			return AppConstants.DELETE_PRODUCT + id;
		}

		else {
			return AppConstants.PRODUCT_ID_DOES_NOT_EXIST + id;
		}
	}

}
