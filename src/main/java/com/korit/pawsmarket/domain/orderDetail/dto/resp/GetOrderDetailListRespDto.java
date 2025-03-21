package com.korit.pawsmarket.domain.orderDetail.dto.resp;

import lombok.Builder;

@Builder
public record GetOrderDetailListRespDto(
        Long orderDetailId,
        String productName


) {
}
