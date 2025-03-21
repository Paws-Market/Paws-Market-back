package com.korit.pawsmarket.domain.order.service;

import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.order.entity.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadOrderService {

    private final OrderRepository orderRepository;

    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
