package com.korit.pawsmarket.domain.orderDetail.dto.resp;

import com.korit.pawsmarket.domain.orderDetail.entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//DB에 저장된 OrderDetail을 가져와서 반환
public record OrderDetailRespDto(
        Long productId,
        int quantity,
        int price, // 주문 당시 원래 가격
        int discountRate,
        int finalPrice, // 개당 최종 가격
        int totalPrice, // 총 결제 금액
        String shippingStatus
) {
    public static OrderDetailRespDto from(OrderDetail orderDetail) {
        return new OrderDetailRespDto(
                orderDetail.getProduct().getProductId(),
                orderDetail.getQuantity(),
                orderDetail.getPrice(),
                orderDetail.getDiscountRate(),
                orderDetail.getFinalPrice(),
                orderDetail.getTotalPrice(),
                orderDetail.getShippingStatus().name()
        );
    }
}
