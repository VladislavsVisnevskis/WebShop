package com.accenture.webshop.service.mapper;

import com.accenture.webshop.domain.ProductCategory;
import com.accenture.webshop.domain.ProductEntity;
import com.accenture.webshop.model.ProductItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {

    ProductMapper mapper = new ProductMapper();

    @Test
    void testMapFromEntity() {
        //Set up
        var entity = createProductEntity();
        var expected = createProductItem();
        //Perform
        var actual = mapper.mapFromEntity(entity);

        assertEquals(actual, expected);
    }

    @Test
    void testMapToEntity() {
        //Set up
        var item = createProductItem();
        var expected = createProductEntity();
        //Perform
        var actual = mapper.mapToEntity(item);

        assertEquals(actual, expected);
    }

    private ProductItem createProductItem() {
        return ProductItem.builder()
                .id(1L)
                .name("product")
                .category("Milk")
                .price(BigDecimal.ONE)
                .count(10)
                .discount(BigDecimal.TEN)
                .description("test")
                .build();
    }

    private ProductEntity createProductEntity() {
        ProductEntity entity = new ProductEntity();
        return entity.setId(1L)
                .setName("product")
                .setCategory(ProductCategory.MILK)
                .setPrice(BigDecimal.ONE)
                .setCount(10)
                .setDiscount(BigDecimal.TEN)
                .setDescription("test");
    }
}