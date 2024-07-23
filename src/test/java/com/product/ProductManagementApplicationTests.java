package com.product;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import com.product.appconstants.AppConstants;
import com.product.model.Category;
import com.product.model.Product;
import com.product.repository.CategoryRepository;
import com.product.repository.ProductRepository;
import com.product.service.CategoryService;
import com.product.service.ProductService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ProductManagementApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl;

	private String baseUrl1;
	@Autowired
	private static RestTemplate restTemplate;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port + "/category";
		baseUrl1 = "http://localhost:" + port + "/product";
	}

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@MockBean
	private CategoryRepository categoryRepository;

	@MockBean
	private ProductRepository productRepository;

	// ------------------Product Class Test Cases -----------------

	@Test
	public void getAllProducts() {
		Product product1 = new Product("Lehenga", "This one of the most famous dress for girls",
				new BigDecimal("3999.00"), new Category());
		Product product2 = new Product("IQOO Z6 PRO", "This One Of The Best Mobile Related Product",
				new BigDecimal("28000.00"), new Category());
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(product1);
		mockProducts.add(product2);

		when(productRepository.findAll()).thenReturn(mockProducts);

		List<Product> products = productService.getAllProducts();

		assertEquals(2, products.size());
		assertEquals("Lehenga", products.get(0).getName());
		assertEquals("This one of the most famous dress for girls", products.get(0).getDescription());
		assertEquals(new BigDecimal("3999.00"), products.get(0).getPrice());

		assertEquals("IQOO Z6 PRO", products.get(1).getName());
		assertEquals("This One Of The Best Mobile Related Product", products.get(1).getDescription());
		assertEquals(new BigDecimal("28000.00"), products.get(1).getPrice());
	}

	@Test
	public void getProductById() throws Exception {

		Product mockProduct = new Product();
		mockProduct.setId(1L);
		mockProduct.setName("Mercedes-Bez");
		mockProduct.setDescription("This one of the most famous car and very expensive cost");
		mockProduct.setPrice(new BigDecimal("10000000.00"));
		mockProduct.setCategory(new Category());

		when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

		Product result = productService.getProductById(1L);

		assertEquals(mockProduct, result);
	}

	@Test
	public void getProductByIdProductNotFound() {
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> productService.getProductById(1L));
	}

	@Test
	public void updateProductById() {
		Product existingProduct = new Product();
		existingProduct.setId(1L);
		existingProduct.setName("Cricket Bat");
		existingProduct.setDescription("This product is used by people playing cricket");
		existingProduct.setPrice(new BigDecimal("5000.00"));
		existingProduct.setCategory(new Category());

		Product updatedProduct = new Product();
		updatedProduct.setId(1L);
		updatedProduct.setName("MRF Bat");
		updatedProduct.setDescription("This bat is very famous");
		updatedProduct.setPrice(new BigDecimal("10000.00"));
		updatedProduct.setCategory(new Category());

		when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

		Product result = productService.updateProductById(updatedProduct, 1L);

		assertEquals(1L, result.getId());
		assertEquals("MRF Bat", result.getName());
		assertEquals("This bat is very famous", result.getDescription());
		assertEquals(new BigDecimal("10000.00"), result.getPrice());
	}

	@Test
	public void deleteProductById() {
		Product mockProduct = new Product();
		mockProduct.setId(1L);

		when(productRepository.existsById(1L)).thenReturn(true);
		doNothing().when(productRepository).deleteById(1L); // Mocking deleteById method

		String result = productService.deleteProductById(1L);

		verify(productRepository).deleteById(1L);

		assertEquals(AppConstants.DELETE_PRODUCT + 1L, result);
	}

	// -----------------Category test cases-------------------------

	@Test
	public void getAllCategories() {
		Category category1 = new Category("Automobile", "This category stores cars information", new ArrayList<>());
		Category category2 = new Category("Hotel Service", "This category stores hotels  information",
				new ArrayList<>());
		List<Category> mockCategories = new ArrayList<>();
		mockCategories.add(category1);
		mockCategories.add(category2);

		when(categoryRepository.findAll()).thenReturn(mockCategories);
		List<Category> result = categoryService.getAllCategories();

		assertEquals(2, result.size());
		assertEquals("Automobile", result.get(0).getName());
		assertEquals("This category stores cars information", result.get(0).getDescription());
		assertEquals("Hotel Service", result.get(1).getName());
		assertEquals("This category stores hotels  information", result.get(1).getDescription());
	}

	@Test
	public void getCategoryById() throws Exception {
		Category mockCategory = new Category();
		mockCategory.setId(1L);
		mockCategory.setName("Automobile");
		mockCategory.setDescription("This category stores cars information");

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

		Category result = categoryService.getCategoryById(1L);

		assertEquals(mockCategory, result);
	}

	@Test
	public void updateCategoryById() {
		Category existingCategory = new Category();
		existingCategory.setId(1L);
		existingCategory.setName("Automobile");
		existingCategory.setDescription("This category stores cars information");

		Category updatedCategory = new Category();
		updatedCategory.setId(1L);
		updatedCategory.setName("Automobile");
		updatedCategory.setDescription("This category stores entire automible related information");

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
		when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);

		Category result = categoryService.updateCategoryById(updatedCategory, 1L);

		verify(categoryRepository).findById(1L);
		verify(categoryRepository).save(existingCategory);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Automobile", result.getName());
		assertEquals("This category stores entire automible related information", result.getDescription());
	}

	@Test
	public void deleteCategoryById() {
		Category mockCategory = new Category();
		mockCategory.setId(1L);

		when(categoryRepository.existsById(1L)).thenReturn(true);

		String result = categoryService.deleteCategoryById(1L);

		verify(categoryRepository).deleteById(1L);

		assertEquals(AppConstants.DELETED_CATEGORY + 1L, result);
	}
	

}
