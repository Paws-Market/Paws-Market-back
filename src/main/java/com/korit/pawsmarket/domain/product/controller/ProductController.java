package com.korit.pawsmarket.domain.product.controller;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductDetailRespDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductListRespDto;
import com.korit.pawsmarket.domain.product.facade.ProductFacade;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.(페이징)(전체 목록 조회 / 카테고리별 조회 가능)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetProductListRespDto>>> getProductList(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "salesQuantity") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @RequestParam(name = "categoryType", required = false) CategoryType categoryType
    ) {

        return ApiResponse.generateResp(
                Status.SUCCESS, null, productFacade
                        .getProductList(pageNo, pageSize, sortBy, direction, categoryType)
                        .getContent()
        );
    }

    @Operation(summary = "상품 상세 조회", description = "상품 ID를 입력하면 해당 상품의 상세 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<GetProductDetailRespDto>> getProductDetail(@PathVariable(name = "productId") Long productId) {
        return ApiResponse.generateResp(Status.SUCCESS, null, productFacade.getProductDetail(productId));
    }
}
