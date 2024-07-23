package com.product.controller;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import com.product.appconstants.AppConstants;
import com.product.model.Category;
import com.product.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;
	// only single constructor @Autowired No need Spring Ioc Container managed
	// Dependency Injection
    public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/createCategory")
	public ResponseEntity<String> createCategory(@RequestBody Category category) {
		String reponse = categoryService.createCategory(category);
		return new ResponseEntity<String>(reponse, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws Exception {
		try {
			Category categoryId = categoryService.getCategoryById(id);
			return new ResponseEntity<>(categoryId, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/allCategories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> allProducts = categoryService.getAllCategories();
		return new ResponseEntity<List<Category>>(allProducts, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long id) {
		String deleteProductById = categoryService.deleteCategoryById(id);
		return new ResponseEntity<String>(deleteProductById, HttpStatus.OK);

	}

	@PutMapping("/updateCategory/{id}")
	public ResponseEntity<Category> updateCategoryById(@RequestBody Category category, @PathVariable Long id) {
		Category updateCategoryById = categoryService.updateCategoryById(category, id);

		return new ResponseEntity<Category>(updateCategoryById, HttpStatus.OK);

	}

}
