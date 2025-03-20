package com.korit.pawsmarket.domain.OrderDetail.entity.repository;

import com.korit.pawsmarket.domain.OrderDetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
