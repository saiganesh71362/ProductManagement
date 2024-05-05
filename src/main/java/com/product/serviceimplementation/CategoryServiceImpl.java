package com.product.serviceimplementation;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.product.appconstants.AppConstants;
import com.product.model.Category;
import com.product.repository.CategoryRepository;
import com.product.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	// only single constructor @Autowired No need Spring Ioc Container managed
	// Dependency Injection
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public String createCategory(Category category) {
	    Long Cid = category.getId();

	    if (Cid == null) {
	        Category saveCategory = categoryRepository.save(category);

	        if (saveCategory != null && saveCategory.getId() != null) {
	            return AppConstants.CATEGORY_ADDED + saveCategory.getId();
	        } else {
	            return AppConstants.CATEGORY_ADDED_FAILD;
	        }
	    } else {
	        return AppConstants.CATEGORY_ADDED_ALREADY + Cid;
	    }
	}

	@Override
	public Category getCategoryById(Long id) throws Exception {
		Optional<Category> byId = categoryRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
		} else {
			throw new Exception(AppConstants.CATEGORY_ID_NOT_FOUND + id); // custom exception
		}

	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> all = categoryRepository.findAll();
		return all;
	}

	@Override
	public Category updateCategoryById(Category category, Long id) {
		Category existCategory = categoryRepository.findById(id).orElse(null);
		if (existCategory != null) {
			existCategory.setName(category.getName());
			existCategory.setDescription(category.getDescription());
			existCategory.setProducts(category.getProducts());
			return categoryRepository.save(existCategory);
		} else {
			return null;
		}
	}

	@Override
	public String deleteCategoryById(Long id) {

		if (categoryRepository.existsById(id)) {
			categoryRepository.deleteById(id);

			return AppConstants.DELETED_CATEGORY + id;
		}

		else {
			return AppConstants.CATEGORY_ID_DOES_NOT_EXIST + id;
		}
	}


}
