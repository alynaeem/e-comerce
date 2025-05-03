package com.e_commerce.store.service.impl;

import com.e_commerce.store.dto.OrderDTO;
import com.e_commerce.store.mapper.OrderMapper;
import com.e_commerce.store.model.Order;
import com.e_commerce.store.repository.OrderRepository;
import com.e_commerce.store.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Convert DTO to Order entity
        Order order = orderMapper.orderDTOToOrder(orderDTO);

        // Save order and return the DTO
        order = orderRepository.save(order);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long id) {
        // Find order by ID or throw exception if not found
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        // Check if the order exists
        Order existingOrder = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Update the fields (add more if necessary)
        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());
        // Add more fields here if needed

        // Save and return updated order as DTO
        existingOrder = orderRepository.save(existingOrder);
        return orderMapper.orderToOrderDTO(existingOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        // Find the order by ID or throw exception if not found
        orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Delete the order
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::orderToOrderDTO)
                .toList();
    }
}