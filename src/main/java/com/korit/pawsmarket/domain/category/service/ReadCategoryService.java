package com.korit.pawsmarket.domain.category.service;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.entity.repository.CategoryRepository;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadCategoryService {

    private final CategoryRepository categoryRepository;

    public Category findByCategoryType (CategoryType categoryType) {
        return categoryRepository.readCategoryByCategoryType(categoryType);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("해당 카테고리를 찾을 수 없습니다."));
    }
}
