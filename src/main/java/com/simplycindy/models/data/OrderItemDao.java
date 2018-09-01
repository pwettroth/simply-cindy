package com.simplycindy.models.data;

import com.simplycindy.models.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public interface OrderItemDao extends CrudRepository<OrderItem, Integer> {

    default List<OrderItem> getOrderItems(List<Integer> orderIds) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (Integer orderId : orderIds) {
            OrderItem item = findOne(orderId);
            orderItems.add(item);
        }

        return orderItems;
    }
}
