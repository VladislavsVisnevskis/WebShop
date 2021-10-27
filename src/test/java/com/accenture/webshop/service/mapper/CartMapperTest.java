package com.accenture.webshop.service.mapper;

import com.accenture.webshop.domain.CartEntity;
import com.accenture.webshop.model.CartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CartMapperTest {
    @Mock
    ProductMapper productMapper;
    @InjectMocks
    CartMapper mapper;

    @Test
    void testMapFromEntity() {
        //Set up
        var entity = createCartEntity();
        var expected = createCartItem();
        //perform
        var actual = mapper.mapFromEntity(entity);

        assertEquals(actual, expected);
    }

    @Test
    void testMapToEntity() {
        //Set up
        var item = createCartItem();
        var expected = createCartEntity();
        //perform
        var actual = mapper.mapToEntity(item);

        assertEquals(actual, expected);
    }

    private CartEntity createCartEntity() {
        CartEntity cartEntity = new CartEntity();
        return cartEntity.setId(1L)
                .setName("cart")
                .setProductEntities(Collections.emptyList());
    }

    private CartItem createCartItem() {
        return CartItem.builder()
                .id(1L)
                .productItems(Collections.emptyList())
                .name("cart")
                .build();
    }
}