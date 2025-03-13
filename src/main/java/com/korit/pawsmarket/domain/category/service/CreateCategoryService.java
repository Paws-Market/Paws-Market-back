package com.korit.pawsmarket.domain.category.service;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.entity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

}
