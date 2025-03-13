package com.korit.pawsmarket.domain.category.controller;

import com.korit.pawsmarket.domain.category.dto.req.CreateCategoryReqDto;
import com.korit.pawsmarket.domain.category.dto.resp.GetCategoryRespDto;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.category.facade.CategoryFacade;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryFacade categoryFacade;

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createCategory(@Valid @RequestBody CreateCategoryReqDto reqDto) {

        categoryFacade.createCategory(reqDto);

        return ApiResponse.generateResp(Status.CREATE, "새로운 카테고리가 추가되었습니다.", null);
    }

    @Operation(summary = "카테고리 목록 조회", description = "카테고리의 전체 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetCategoryRespDto>>> getCategoryList() {
        return ApiResponse.generateResp(Status.SUCCESS, null, categoryFacade.getCategoryList());
    }
}
