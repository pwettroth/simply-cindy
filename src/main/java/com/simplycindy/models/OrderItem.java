package com.simplycindy.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private int productId;

    @NotNull
    private String product;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String size;

    @NotNull
    private String color;

    private String custom;

    @NotNull
    private int quantity;

    public OrderItem(int productId, String product, BigDecimal price, String size, String color, String custom, int quantity) {
        this.productId = productId;
        this.product = product;
        this.price = price;
        this.size = size;
        this.color = color;
        this.custom = custom;
        this.quantity = quantity;
    }

    public OrderItem() {
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct() { return product; }

    public void setProduct(String product) { this.product = product; }
}

