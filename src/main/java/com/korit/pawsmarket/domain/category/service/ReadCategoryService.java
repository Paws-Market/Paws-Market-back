package com.korit.pawsmarket.domain.category.service;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.entity.repository.CategoryRepository;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCategoryService {

    private final CategoryRepository categoryRepository;

    public Category findByCategoryType (CategoryType categoryType) {
        return categoryRepository.readCategoryByCategoryType(categoryType);
    }
}
