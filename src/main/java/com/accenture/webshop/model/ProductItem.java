package com.accenture.webshop.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.StringJoiner;

@Validated
public class ProductItem {

    private Long id;
    @NotBlank(message = "Enter product name")
    @Pattern(regexp = "[a-zA-z]*", message = "The name should contain only letters")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "Invalid product price")
    @Digits(integer=3, fraction=2)
    private BigDecimal price;
    @NotNull(message = "Enter product category")
    private String category;
    @DecimalMin(value = "0.0", inclusive = true, message = "Invalid product discount")
    @DecimalMax(value = "100.0", inclusive = true, message = "Invalid product discount")
    private BigDecimal discount;
    private Integer count;
    @Size(max = 250, message = "Too long description")
    private String description;
    private BigDecimal actualPrice;

    public ProductItem() {
    }

    public ProductItem(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.category = builder.category;
        this.discount = builder.discount;
        this.count = builder.count;
        this.description = builder.description;
        this.actualPrice = discount == null ? price : price.multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Integer getCount() {
        return count;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public ProductItem setId(Long id) {
        this.id = id;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem that = (ProductItem) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(price, that.price)
                && Objects.equals(category, that.category)
                && Objects.equals(discount, that.discount)
                && Objects.equals(count, that.count)
                && Objects.equals(description, that.description)
                && Objects.equals(actualPrice, that.actualPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, discount, count, description, actualPrice);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductItem.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("category=" + category)
                .add("discount=" + discount)
                .add("count=" + count)
                .add("description='" + description + "'")
                .add("actualPrice='" + actualPrice + "'")
                .toString();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private BigDecimal price;
        private String category;
        private BigDecimal discount;
        private Integer count;
        private String description;

        private String actualPrice;

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

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public Builder count(Integer count) {
            this.count = count;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public ProductItem build() {
            return new ProductItem(this);
        }
    }
}
