package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction);
    Page<ProductResponse> getFilteredProducts(String name, Long categoryId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, int page, int size, String sortBy, String direction);
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
