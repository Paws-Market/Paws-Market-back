package com.korit.pawsmarket.domain.order.service;

import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.order.entity.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderService {

    private final OrderRepository orderRepository;

    public void createOrder(Order order) { orderRepository.save(order); }
}
