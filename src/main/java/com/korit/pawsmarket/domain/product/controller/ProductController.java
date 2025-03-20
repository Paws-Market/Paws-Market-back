package com.korit.pawsmarket.domain.product.controller;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.dto.req.CreateProductReqDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductDetailRespDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductListRespDto;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.facade.ProductFacade;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "상품 추가 생성", description = "새로운 상품을 등록하는 기능입니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createProduct(@Valid @RequestBody CreateProductReqDto reqDto) {
        productFacade.createProduct(reqDto);
        return ApiResponse.generateResp(Status.CREATE, "새로운 상품 등록되었습니다.", null);
    }

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.(페이징)(전체 목록 조회 / 카테고리별 조회 가능)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetProductListRespDto>>> getProductList(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "salesQuantity") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @RequestParam(name = "categoryType", required = false) CategoryType categoryType,
            @RequestParam(name = "petType", required = false) PetType petType,
            @RequestParam(name = "search", required = false) String search
    ) {
        Page<GetProductListRespDto> productList = productFacade
                .getProductList(pageNo, pageSize, sortBy, direction, categoryType, petType, search);
        String totalPages = String.valueOf(productList.getTotalPages());

        return ApiResponse.generateResp(Status.SUCCESS, totalPages, productList.getContent());
    }

    @Operation(summary = "상품 상세 조회", description = "상품 ID를 입력하면 해당 상품의 상세 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<GetProductDetailRespDto>> getProductDetail(
            @PathVariable(name = "productId") Long productId
    ) {
        return ApiResponse.generateResp(Status.SUCCESS, null, productFacade.getProductDetail(productId));
    }

    @Operation(summary = "상품 입고 주문", description = "상품 입고 주문을 하면 일정 시간 이후에 재고가 입고됩니다.")
    @PostMapping("/{productId}/stock")
    public ResponseEntity<ApiResponse<Void>> scheduleStockArrival(
            @PathVariable(name = "productId") Long productId,
            @RequestParam(name = "quantity") int quantity
    ) {
        productFacade.scheduleStockArrival(productId, quantity);
        return ApiResponse.generateResp(Status.CREATE, "상품 입고 주문이 완료되었습니다.", null);
    }
}
