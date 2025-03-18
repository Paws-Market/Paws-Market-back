package com.korit.pawsmarket.domain.category.dto.resp;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import lombok.Builder;

@Builder
public record GetCategoryRespDto(
        Long categoryId,
        CategoryType categoryType,
        String categoryName
) {

    public static GetCategoryRespDto from(Category category) {
        return GetCategoryRespDto.builder()
                .categoryId(category.getCategoryId())
                .categoryType(category.getCategoryType())
                .categoryName(category.getCategoryName())
                .build();
    }
}
