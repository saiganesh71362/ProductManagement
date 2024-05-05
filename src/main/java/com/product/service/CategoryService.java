package com.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.model.Category;

@Service
public interface CategoryService
{
	   public String createCategory(Category category);
	   public Category getCategoryById(Long id) throws Exception;
	   public List<Category> getAllCategories();
	   public Category updateCategoryById(Category category, Long id);
	   public String deleteCategoryById(Long id);


}
