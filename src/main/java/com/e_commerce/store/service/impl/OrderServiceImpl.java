package com.e_commerce.store.service.impl;

import com.e_commerce.store.dto.OrderDTO;
import com.e_commerce.store.mapper.OrderMapper;
import com.e_commerce.store.model.Order;
import com.e_commerce.store.model.OrderStatus;
import com.e_commerce.store.repository.OrderRepository;
import com.e_commerce.store.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {


        if (orderDTO.getStatus() == null) {
            orderDTO.setStatus(OrderStatus.PENDING.name());
        }

        Order order = modelMapper.map(orderDTO, Order.class);
        order = orderRepository.save(order);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update status of a cancelled order");
        }

        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot delete shipped/delivered orders");
        }

        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::orderToOrderDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::orderToOrderDTO)
                .toList();
    }
}