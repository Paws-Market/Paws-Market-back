package com.korit.pawsmarket.domain.category.facade;

import com.korit.pawsmarket.domain.category.dto.req.CreateCategoryReqDto;
import com.korit.pawsmarket.domain.category.dto.resp.GetCategoryRespDto;
import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.service.CreateCategoryService;
import com.korit.pawsmarket.domain.category.service.ReadCategoryService;
import com.korit.pawsmarket.global.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
@RequiredArgsConstructor
public class CategoryFacade {

    private final CreateCategoryService createCategoryService;
    private final ReadCategoryService readCategoryService;

    public void createCategory(CreateCategoryReqDto reqDto) {
        Category foundCategory = readCategoryService.findByCategoryType(reqDto.categoryType());

        if (foundCategory != null) {
            throw new DuplicateException("이미 존재하는 카테고리입니다.");
        }

        Category category = reqDto.of();

        createCategoryService.createCategory(category);
    }

    @Transactional(readOnly = true)
    public List<GetCategoryRespDto> getCategoryList() {
        return readCategoryService.findAll().stream().map(GetCategoryRespDto::from).toList();
    }
}
