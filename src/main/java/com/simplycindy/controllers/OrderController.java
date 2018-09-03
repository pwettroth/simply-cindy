package com.simplycindy.controllers;

import com.simplycindy.models.*;
import com.simplycindy.models.data.OrderDataDao;
import com.simplycindy.models.data.OrderItemDao;
import com.simplycindy.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDataDao orderDataDao;

    @RequestMapping(value = "singleProduct", method = RequestMethod.POST)
    public String buyProduct(@ModelAttribute @Valid OrderItem newOrderItem,
                             @RequestParam int productId, Model model) {

        Product aProduct = productDao.findOne(productId);

        model.addAttribute("title", aProduct.getName());
        model.addAttribute("product", aProduct);

        newOrderItem.setProductId(productId);
        newOrderItem.setPrice(aProduct.getPrice());

        orderItemDao.save(newOrderItem);

        return "product/singleProductDisplay";
    }

    @RequestMapping("")
    public String view(Model model) {

        return "orders/view";
    }


    // Build the orderMap up based on the order data passed in
    private Map<Integer, List<OrderLog>> buildOrderMap(Iterable<OrderData> orderDataList) {
        Map<Integer, List<OrderLog>> map = new HashMap<>();

        for(OrderData orderData : orderDataList) {
            Integer key = orderData.getId();
            List<OrderLog> values = new ArrayList<>();

            List<Integer> orderItemIds = orderDataDao.getOrderItemIds(key);
            List<OrderItem> orderItems = orderItemDao.getOrderItems(orderItemIds);

            for (OrderItem orderItem : orderItems) {
                values.add(new OrderLog(orderItem.getProductId(), orderItem.getProduct(), orderItem.getPrice(), orderItem.getSize(),
                        orderItem.getColor(), orderItem.getCustom(), orderItem.getQuantity()));
            }
            map.put(key, values);
        }

        return map;
    }

    // Build the OrderMap based on the orderId in the OrderData table and build up the information
    private Map<Integer, List<OrderLog>> buildOrderMapByOrderId(int orderId) {
        Iterable<OrderData> orderDataList = new ArrayList<OrderData>();
        ((ArrayList<OrderData>) orderDataList).add(orderDataDao.findOne(orderId));

        Map<Integer, List<OrderLog>> map = buildOrderMap(orderDataList);

        return map;
    }

    @RequestMapping("admin")
    public String viewOrders(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Simply Cindy");

        Map<Integer, List<OrderLog>> map = buildOrderMap(orderDataDao.findAll());

        model.addAttribute("order", map);
        return "orders/adminOrders";
    }
}
