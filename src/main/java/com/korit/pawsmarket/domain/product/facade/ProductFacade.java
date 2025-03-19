package com.korit.pawsmarket.domain.product.facade;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.category.service.ReadCategoryService;
import com.korit.pawsmarket.domain.product.dto.req.CreateProductReqDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductDetailRespDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductListRespDto;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.service.CreateProductService;
import com.korit.pawsmarket.domain.product.service.ReadProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ReadProductService readProductService;
    private final ReadCategoryService readCategoryService;
    private final CreateProductService createProductService;

    @Transactional(readOnly = true)
    public Page<GetProductListRespDto> getProductList(
            int pageNo, int pageSize, String sortBy, String direction,
            CategoryType categoryType, PetType petType, String search
    ) {
        List<PetType> petTypes;

        if (petType == null) {                  // 전체보기
            petTypes = List.of(PetType.COMMON, PetType.DOG, PetType.CAT);
        } else if (petType == PetType.DOG) {    // 강아지
            petTypes = List.of(PetType.COMMON, PetType.DOG);
        } else if (petType == PetType.CAT) {    // 고양이
            petTypes = List.of(PetType.COMMON, PetType.CAT);
        } else {
            // 잘못된 값이 들어왔을 때의 기본값 처리 (옵션)
            petTypes = List.of(PetType.COMMON, PetType.DOG, PetType.CAT);
        }


        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(Sort.Direction.fromString(direction), sortBy));

        if (categoryType == null && (search == null || search.isEmpty())) {
            // 1-1. 카테고리 널, 검색어 널 (전체 상품 조회)
            return readProductService.findAllQuery(pageable, petTypes)
                    .map(GetProductListRespDto::from);
        } else if (categoryType == null) {
            // 1-2. 카테고리 널, 검색어 있음 (검색만 수행)
            return readProductService.findAllBySearchQuery(pageable, search, petTypes)
                    .map(GetProductListRespDto::from);
        } else if (search == null || search.isEmpty()) {
            // 2-1. 카테고리 있음, 검색어 널 (카테고리 기준 조회)
            return readProductService.findAllByCategoryQuery(pageable, categoryType, petTypes)
                    .map(GetProductListRespDto::from);
        } else {
            // 2-2. 카테고리 있음, 검색어 있음 (카테고리 + 검색어 조회)
            return readProductService.findAllByCategoryAndSearchQuery(pageable, categoryType, search, petTypes)
                    .map(GetProductListRespDto::from);
        }
    }

    @Transactional(readOnly = true)
    public GetProductDetailRespDto getProductDetail(Long productId) {
        Product product = readProductService.findByIdQuery(productId);
        return GetProductDetailRespDto.from(product);
    }

    public void createProduct(CreateProductReqDto reqDto) {
        Category category = readCategoryService.findById(reqDto.categoryId());
        Product product = reqDto.of(category);
        createProductService.save(product);
    }
}
