package com.simplycindy.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderLog {

    private int productId;

    private String product;

    private BigDecimal price;

    private String size;

    private String color;

    private String custom;

    private int quantity;

    public OrderLog(int productId, String product, BigDecimal price, String size, String color, String custom, int quantity) {
        this.productId = productId;
        this.product = product;
        this.price = price;
        this.size = size;
        this.color = color;
        this.custom = custom;
        this.quantity = quantity;
    }

    public OrderLog() {
    }

    public int getProductId() {
        return productId;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getCustom() {
        return custom;
    }

    public int getQuantity() {
        return quantity;
    }
}