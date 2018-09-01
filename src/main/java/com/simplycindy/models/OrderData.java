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

    public OrderData(String orderItemIds) {
        this.orderItemIds = orderItemIds;
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
}
