package com.korit.pawsmarket.domain.order.dto.resp;

import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.order.enums.OrderStatus;
import com.korit.pawsmarket.domain.orderDetail.dto.resp.OrderDetailRespDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetOrderListRespDto(
        Long orderId,
        Long userId,
        LocalDateTime createdAt, // 주문 날짜
        List<OrderDetailRespDto> orderDetailList, // 주문 상세 내역 리스트 (DTO 사용)
        OrderStatus orderStatus  // 주문 상태 (결제 대기, 완료, 취소 등)
) {
    public static GetOrderListRespDto from(Order order) {
        return GetOrderListRespDto.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .createdAt(order.getCreatedAt())
                .orderDetailList(order.getOrderDetails().stream()
                        .map(OrderDetailRespDto::from) // OrderDetail → CreateOrderDetailRespDto로 변환
                        .toList()) // OrderDetail을 CreateOrderDetailRespDto로 변환
                .orderStatus(order.getOrderStatus())
                .build();

    }
}
