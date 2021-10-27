package com.accenture.webshop.service.impl;

import com.accenture.webshop.domain.ProductEntity;
import com.accenture.webshop.model.ProductItem;
import com.accenture.webshop.repository.ProductRepository;
import com.accenture.webshop.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductServiceImpl.class)
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl service;
    @MockBean
    private ProductMapper mapper;
    @MockBean
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl(repository, mapper);
    }

    @Test
    void testFindAllProducts() {
        //Set up
        when(repository.findAll()).thenReturn(createListOfProducts());
        when(mapper.mapFromEntity(any())).thenReturn(ProductItem.builder().build());
        var expected = createListofProductItems();
        //Perform
        List<ProductItem> actual = service.findAllProducts();

        assertEquals(actual.size(), expected.size());
        assertEquals(actual.get(0), expected.get(0));
    }

    @Test
    void testGetProductsById() {
        //Set up
        when(repository.getById(any())).thenReturn(createProductEntity());
        when(mapper.mapFromEntity(any()))
                .thenReturn(ProductItem.builder()
                        .id(1L)
                        .build());
        var expected = createProductItem();
        //Perform
        Optional<ProductItem> actual = service.getProductById(1L);

        assertEquals(actual.get(), expected);
    }

    @Test
    void testSaveProducts() {
        //Set up
        when(repository.save(any())).thenReturn(createProductEntity());
        when(mapper.mapFromEntity(any()))
                .thenReturn(ProductItem.builder()
                        .id(1L)
                        .build());
        var expected = createProductItem();
        //Perform
        ProductItem actual = service.saveProduct(createProductItem());

        assertEquals(actual, expected);
    }

    @Test
    void testUpdateProducts() {
        //Set up
        when(repository.save(any())).thenReturn(createProductEntity().setName("edit"));
        when(repository.getById(any())).thenReturn(createProductEntity());
        when(mapper.mapFromEntity(any()))
                .thenReturn(ProductItem.builder()
                        .id(1L)
                        .name("edit")
                        .build());
        var expected = ProductItem.builder()
                .id(1L)
                .name("edit")
                .build();
        //Perform
        ProductItem actual = service.updateProductById(1L, createProductItem().builder()
                .name("edit")
                .build());

        assertEquals(actual, expected);
    }

    @Test
    void testDeleteProducts() {
        //Set up
//        doAnswer()
        when(repository.getById(any())).thenReturn(createProductEntity());
        var expected = createProductItem().builder()
                .build();
        //Perform
        service.deleteProductById(1L);

        verify(repository, times(1)).getById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    private ProductItem createProductItem() {
        return ProductItem.builder()
                .id(1L)
                .build();
    }

    private ProductEntity createProductEntity() {
        ProductEntity entity = new ProductEntity();
        return entity.setId(1L);
    }

    private List<ProductItem> createListofProductItems() {
        return Collections.singletonList(ProductItem.builder().build());
    }

    private List<ProductEntity> createListOfProducts() {
        return Collections.singletonList(new ProductEntity());
    }
}