package com.korit.pawsmarket.domain.order.dto.req;

import com.korit.pawsmarket.domain.OrderDetail.dto.req.CreateOrderDetailReqDto;
import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.order.enums.OrderStatus;
import com.korit.pawsmarket.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderReqDto(
        @Schema(description = "주문자 ID", example = "1")
        @NotNull(message = "유저 ID는 필수입니다.")
        Long userId,

        @Schema(description = "수령인", example = "백도은")
        @NotBlank(message = "수령인 입력은 필수입니다.")
        String receiverName,

        @Schema(description = "배송지", example = "서울시 강남구")
        @NotBlank(message = "배송지 입력은 필수입니다.")
        String shippingAddress,

        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호 입력은 필수입니다.")
        String receiverPhone,

        @Schema(description = "주문 상세", example = "")
        @NotNull(message = "주문 상세 내역은 필수입니다.")
        List<CreateOrderDetailReqDto> orderDetails
) {
        public Order toEntity(User user) {
                return Order.builder()
                        .user(user)
                        .receiverName(receiverName)
                        .shippingAddress(shippingAddress)
                        .receiverPhone(receiverPhone)
                        .orderStatus(OrderStatus.PAYMENT_PENDING)
                        .build();
        }
}
