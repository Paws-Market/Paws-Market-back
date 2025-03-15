package com.korit.pawsmarket.domain.product.dto.req;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record CreateProductReqDto(

        @Schema(description = "카테고리 유형", example = "FOOD")
        @NotNull(message = "해당 상품의 카테고리를 입력해주세요.")
        Long categoryId,

        @Schema(description = "상품명", example = "데일리 트릿 (연어/닭가슴살/명태)")
        @NotBlank(message = "상품명을 입력해주세요.")
        String productName,

        @Schema(description = "가격", example = "22000")
        @NotNull(message = "가격을 입력해주세요")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        Integer price,

        @Schema(description = "대표 이미지", example = "대표 이미지 url")
        @NotBlank(message = "대표 이미지를 등록해주세요.")
        String imageUrl,

        @Schema(description = "재고", example = "10")
        @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
        Integer stock,

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
        Integer discountRate
) {

    public Product of(Category category) {
        return Product.builder()
                .category(category)
                .productName(this.productName)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .stock(stock == null ? 0 : stock)                       // 재고 입력하지 않으면 기본값 0
                .saleStatus(SaleStatus.ON_SALE)                         // 상품 등록시 판매 상태 기본값 판매중
                .descriptionImageUrl1(this.descriptionImageUrl1)
                .descriptionImageUrl2(this.descriptionImageUrl2)
                .descriptionImageUrl3(this.descriptionImageUrl3)
                .salesQuantity(0)                                       // 상품 등록시 판매수량 기본값 0
                .petType(this.petType)
                .discountRate(discountRate == null ? 0 : discountRate)  // 할인율 입력하지 않으면 기본값 0
                .build();
    }
}
