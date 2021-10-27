package com.accenture.webshop.service.mapper;

import com.accenture.webshop.domain.ProductCategory;
import com.accenture.webshop.domain.ProductEntity;
import com.accenture.webshop.model.ProductItem;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ProductMapper {

    public ProductItem mapFromEntity(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        return ProductItem.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .category(productEntity.getCategory().toString())
                .count(productEntity.getCount())
                .discount(productEntity.getDiscount())
                .description(productEntity.getDescription())
                .build().setId(productEntity.getId());
    }

    public ProductEntity mapToEntity(ProductItem productItem) {
        if (productItem == null) {
            return null;
        }
        return new ProductEntity()
                .setId(productItem.getId())
                .setName(productItem.getName())
                .setPrice(productItem.getPrice())
                .setCategory(ProductCategory.valueOf(productItem.getCategory().toUpperCase(Locale.ROOT)))
                .setCount(productItem.getCount())
                .setDiscount(productItem.getDiscount())
                .setDescription(productItem.getDescription());
    }
}
