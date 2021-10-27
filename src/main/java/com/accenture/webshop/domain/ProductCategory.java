package com.accenture.webshop.domain;

public enum ProductCategory {
    VEGETABLES ("Vegetables"),
    FRUITS ("Fruits"),
    MEAT("Meat"),
    FISH("Fish"),
    SWEETS("Sweets"),
    BAKERY("Bakery"),
    BEVERAGE("Beverage"),
    ALCOHOL("Alcohol"),
    MILK("Milk"),
    SAUCES("Sauces"),
    HOUSE("House stuff"),
    FROZEN("Frozen"),
    CEREALS("Cereals"),
    OTHER("Other");

    private final String category;

    ProductCategory(String category){
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return category;
    }
}
