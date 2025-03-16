package com.korit.pawsmarket.domain.product.dto.resp;

import com.korit.pawsmarket.domain.category.dto.resp.GetCategoryRespDto;
import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import lombok.Builder;

@Builder
public record GetProductDetailRespDto(
        Long productId,
        GetCategoryRespDto category,
        String productName,             // 상품명
        int price,                      // 가격
        String imageUrl,                // 대표 이미지
        SaleStatus saleStatus,          // 판매상태
        String descriptionImageUrl1,    // 상세설명 이미지1
        String descriptionImageUrl2,    // 상세설명 이미지2
        String descriptionImageUrl3,    // 상세설명 이미지3
        PetType petType,                // 펫 타입
        int discountRate                // 할인율
) {

    public static GetProductDetailRespDto from(Product product) {
        return GetProductDetailRespDto.builder()
                .productId(product.getProductId())
                .category(GetCategoryRespDto.from(product.getCategory()))
                .productName(product.getProductName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .saleStatus(product.getSaleStatus())
                .descriptionImageUrl1(product.getDescriptionImageUrl1())
                .descriptionImageUrl2(product.getDescriptionImageUrl2())
                .descriptionImageUrl3(product.getDescriptionImageUrl3())
                .petType(product.getPetType())
                .discountRate(product.getDiscountRate())
                .build();
    }
}
