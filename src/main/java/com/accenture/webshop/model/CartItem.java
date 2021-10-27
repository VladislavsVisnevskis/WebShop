package com.accenture.webshop.model;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class CartItem {

    private Long id;
    private String name;
    private List<ProductItem> productItems;

    public CartItem(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.productItems = builder.productItems;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id)
                && Objects.equals(name, cartItem.name)
                && Objects.equals(productItems, cartItem.productItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productItems);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CartItem.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("productItems=" + productItems)
                .toString();
    }


    public static final class Builder {
        private Long id;
        private String name;
        private List<ProductItem> productItems;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder productItems(List<ProductItem> productItems) {
            this.productItems = productItems;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
