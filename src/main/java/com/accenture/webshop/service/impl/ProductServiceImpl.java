package com.accenture.webshop.service.impl;

import com.accenture.webshop.domain.ProductEntity;
import com.accenture.webshop.model.ProductItem;
import com.accenture.webshop.repository.ProductRepository;
import com.accenture.webshop.service.ProductService;
import com.accenture.webshop.service.exception.ProductUpdateException;
import com.accenture.webshop.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductItem> findAllProducts() {
        List<ProductEntity> productEntities = repository.findAll();
        return productEntities.stream()
                .map(mapper::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductItem> getProductById(Long id) {
        return ofNullable(repository.getById(id)).map(mapper::mapFromEntity);
    }

    @Override
    public ProductItem saveProduct(ProductItem product) {
        ProductEntity productEntity = repository.save(mapper.mapToEntity(product));
        return mapper.mapFromEntity(productEntity);
    }

    @Override
    public ProductItem updateProductById(Long id, ProductItem product) throws ProductUpdateException{
        Optional<ProductEntity> productToUpdate = ofNullable(repository.getById(id));
        if (productToUpdate.isEmpty()) {
            throw new ProductUpdateException("Product doesn't exist");
        }
        ProductEntity productEntity = mapper.mapToEntity(product);
        return mapper.mapFromEntity(repository.save(productEntity));
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<ProductEntity> productToUpdate = ofNullable(repository.getById(id));
        if (productToUpdate.isEmpty()) {
            throw new ProductUpdateException("Product doesn't exist");
        }
        repository.deleteById(id);
    }
}
