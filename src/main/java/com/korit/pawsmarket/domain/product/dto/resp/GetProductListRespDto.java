package com.korit.pawsmarket.domain.product.dto.resp;

import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import lombok.Builder;

@Builder
public record GetProductListRespDto(
        Long productId,
        String imageUrl,
        String productName,
        int price,
        int discountRate,
        SaleStatus saleStatus,
        int salesQuantity
) {

    public static GetProductListRespDto from(Product product) {
        return GetProductListRespDto.builder()
                .productId(product.getProductId())
                .imageUrl(product.getImageUrl())
                .productName(product.getProductName())
                .price(product.getPrice())
                .discountRate(product.getDiscountRate())
                .saleStatus(product.getSaleStatus())
                .salesQuantity(product.getSalesQuantity())
                .build();
    }
}
