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

    default List<OrderItem> getOrderItems(List<Integer> orderItemIds) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (Integer orderItemId : orderItemIds) {
            OrderItem item = findOne(orderItemId);
            orderItems.add(item);
        }

        return orderItems;
    }
}
