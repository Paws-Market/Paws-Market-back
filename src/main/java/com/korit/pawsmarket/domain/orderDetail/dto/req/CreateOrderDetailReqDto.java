package com.korit.pawsmarket.domain.orderDetail.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDetailReqDto(
        @Schema(description = "상품 ID", example = "1")
        @NotNull(message = "상품 ID는 필수입니다.")
        Long productId,

        @Schema(description = "주문 수량", example = "30")
        @NotNull(message = "주문 수량은 필수입니다.")
        @Min(value = 1, message = "최소 1개의 상품을 주문해야 합니다.")
        int quantity
) {

}
