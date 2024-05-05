package com.product.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.product.model.Product;

@Service
public interface ProductService 
{
   public String createProduct(Product product);
   public Product getProductById(Long id) throws Exception;
   public List<Product> getAllProducts();
   public Product updateProductById(Product product,Long id);
   public String deleteProductById(Long id);

}
