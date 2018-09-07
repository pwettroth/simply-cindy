package com.simplycindy.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class OrderData {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String orderItemIds;

    @NotNull
    private String email;

    public OrderData(String orderItemIds, String email) {
        this.orderItemIds = orderItemIds;
        this.email = email;
    }

    public OrderData() {
    }

    public int getId() {
        return id;
    }

    public String getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(String orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
