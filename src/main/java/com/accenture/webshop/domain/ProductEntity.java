package com.accenture.webshop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "count")
    private Integer count;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "productEntities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartEntity> cartSet;

    public Long getId() {
        return id;
    }

    public ProductEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public ProductEntity setCategory(ProductCategory category) {
        this.category = category;
        return this;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public ProductEntity setDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public ProductEntity setCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(price, that.price)
                && category == that.category
                && Objects.equals(discount, that.discount)
                && Objects.equals(count, that.count)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, discount, count, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("category=" + category)
                .add("discount=" + discount)
                .add("count=" + count)
                .add("description='" + description + "'")
                .toString();
    }
}
