package com.simplycindy.models.data;

import com.simplycindy.models.OrderData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public interface OrderDataDao extends CrudRepository <OrderData, Integer> {

    default List<Integer> getOrderItemIds(int orderId) {

        List<Integer> orderItemIds = new ArrayList<>();
        OrderData order = findOne(orderId);
        String orderItemIdString = order.getOrderItemIds();

        String[] items = orderItemIdString.split(",");

        for (String item : items) {
            int itemNumber = Integer.parseInt(item);
            orderItemIds.add(itemNumber);
        }

        return orderItemIds;
    }
}
