package com.korit.pawsmarket.domain.product.dto.resp;

import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import lombok.Builder;

@Builder
public record GetLowStockProductRespDto(
        Long productId,
        String imageUrl,
        String productName,
        SaleStatus saleStatus,
        int stock
) {

    public static GetLowStockProductRespDto from(Product product) {
        return GetLowStockProductRespDto.builder()
                .productId(product.getProductId())
                .imageUrl(product.getImageUrl())
                .productName(product.getProductName())
                .saleStatus(product.getSaleStatus())
                .stock(product.getStock())
                .build();
    }
}
