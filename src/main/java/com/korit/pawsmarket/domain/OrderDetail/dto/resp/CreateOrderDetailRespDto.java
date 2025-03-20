package com.korit.pawsmarket.domain.OrderDetail.dto.resp;

import com.korit.pawsmarket.domain.OrderDetail.entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDetailRespDto(
        @Schema(description = "상품 ID", example = "1")
        @NotNull(message = "상품 ID는 필수입니다.")
        Long productId,

        @Schema(description = "주문 수량", example = "30")
        @NotNull(message = "주문 수량은 필수입니다.")
        @Min(value = 1, message = "최소 1개의 상품을 주문해야 합니다.")
        int quantity,

        @Schema(description = "할인율", example = "10")
        int discountRate,

        @Schema(description = "배송 상태", example = "SHIPPED")
        @NotBlank(message = "주문 상태는 필수입니다.")
        String shippingStatus
) {
    public static CreateOrderDetailRespDto from(OrderDetail orderDetail) {
        return new CreateOrderDetailRespDto(
                orderDetail.getProduct().getProductId(),
                orderDetail.getQuantity(),
                orderDetail.getDiscountRate(),
                orderDetail.getShippingStatus().name()
        );
    }
}
