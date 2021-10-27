package com.accenture.webshop.service.mapper;

import com.accenture.webshop.domain.CartEntity;
import com.accenture.webshop.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    private final ProductMapper productMapper;

    public CartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartItem mapFromEntity(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }
        return CartItem.builder()
                .id(cartEntity.getId())
                .name(cartEntity.getName())
                .productItems(cartEntity.getProductEntities().stream()
                        .map(productMapper::mapFromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public CartEntity mapToEntity(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        return new CartEntity()
                .setId(cartItem.getId())
                .setName(cartItem.getName())
                .setProductEntities(cartItem.getProductItems().stream()
                        .map(productMapper::mapToEntity)
                        .collect(Collectors.toList()));
    }
}
