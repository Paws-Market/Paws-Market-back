package com.korit.pawsmarket.domain.product.dto.req;

import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record UpdateProductReqDto(

        @Schema(description = "카테고리 유형", example = "FOOD")
        @NotNull(message = "해당 상품의 카테고리를 입력해주세요.")
        Long categoryId,

        @Schema(description = "상품명", example = "데일리 트릿 (연어/닭가슴살/명태)")
        @NotBlank(message = "상품명을 입력해주세요.")
        String productName,

        @Schema(description = "가격", example = "22000")
        @NotNull(message = "가격을 입력해주세요")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        int price,

        @Schema(description = "대표 이미지", example = "대표 이미지 url")
        @NotBlank(message = "대표 이미지를 등록해주세요.")
        String imageUrl,

        @Schema(description = "판매 상태", example = "ON_SALE")
        @NotNull(message = "판매 상태를 선택해주세요.     ON_SALE(판매중), OUT_OF_STOCK(일시 품절), DISCONTINUED(단종/판매 중단)")
        @Enumerated(EnumType.STRING)
        SaleStatus saleStatus,

        @Schema(description = "상품 설명 이미지 1", example = "상품 설명 이미지 url 1")
        @NotBlank(message = "상품 설명 이미지를 등록해주세요.")
        String descriptionImageUrl1,

        @Schema(description = "상품 설명 이미지 2", example = "상품 설명 이미지 url 2")
        String descriptionImageUrl2,

        @Schema(description = "상품 설명 이미지 3", example = "상품 설명 이미지 url 3")
        String descriptionImageUrl3,

        @Schema(description = "해당 상품의 사용 대상이 되는 반려동물 유형", example = "COMMON")
        @NotNull(message = "반려동물 유형을 선택해주세요. COMMON(공용), DOG(강아지), CAT(고양이)")
        @Enumerated(EnumType.STRING)
        PetType petType,

        @Schema(description = "할인율", example = "0")
        @Range(min = 0, max = 100, message = "할인율은 0 이상 100 이하여야 합니다.")
        @NotNull(message = "할인율을 입력해주세요.")
        int discountRate
) {
}
