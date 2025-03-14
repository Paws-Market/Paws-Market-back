package com.korit.pawsmarket.domain.product.controller;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductListRespDto;
import com.korit.pawsmarket.domain.product.facade.ProductFacade;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/Products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.(페이징)(전체 목록 조회 / 카테고리별 조회 가능)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetProductListRespDto>>> getProductList(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "salesQuantity") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) CategoryType categoryType
    ) {

        return ApiResponse.generateResp(
                Status.SUCCESS, null, productFacade
                        .getProductList(pageNo, pageSize, sortBy, direction, categoryType)
                        .getContent()
        );
    }
}
