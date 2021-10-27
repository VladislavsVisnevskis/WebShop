package com.accenture.webshop.service;

import com.accenture.webshop.model.ProductItem;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductItem> findAllProducts();

    Optional<ProductItem> getProductById(Long id);

    ProductItem saveProduct(ProductItem product);

    ProductItem updateProductById(Long id, ProductItem product);

    void deleteProductById(Long id);

}
