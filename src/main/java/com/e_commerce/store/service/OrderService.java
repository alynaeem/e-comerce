package com.e_commerce.store.service;

import com.e_commerce.store.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(Long id);
    OrderDTO updateOrder(OrderDTO orderDTO);
    void deleteOrder(Long id);
    List<OrderDTO> getAllOrders();
}
