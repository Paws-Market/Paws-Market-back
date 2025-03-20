package com.korit.pawsmarket.domain.order.entity.repository;

import com.korit.pawsmarket.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {

}
