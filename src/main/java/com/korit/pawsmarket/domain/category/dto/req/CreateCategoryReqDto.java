package com.korit.pawsmarket.domain.category.dto.req;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryReqDto(

        @Schema(description = "카테고리 유형", example = "FOOD")
        @NotNull(message = "카테고리 유형을 선택해주세요. " +
                "FOOD(사료&분유), TREAT(간식), SUPPLEMENT(영양제), TOY(장난감&훈련용품), BATH(목욕용품)")
        @Enumerated(EnumType.STRING)  // enum을 사용할 때 @Enumerated 어노테이션 추가
        CategoryType categoryType
) {

    public Category of() {
        return Category.builder()
                .categoryType(this.categoryType)
                .build();
    }
}
