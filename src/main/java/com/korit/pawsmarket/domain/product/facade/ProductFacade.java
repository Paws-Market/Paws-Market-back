package com.korit.pawsmarket.domain.product.facade;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.category.service.ReadCategoryService;
import com.korit.pawsmarket.domain.product.dto.req.CreateProductReqDto;
import com.korit.pawsmarket.domain.product.dto.req.UpdateProductReqDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetLowStockProductRespDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductDetailRespDto;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductListRespDto;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import com.korit.pawsmarket.domain.product.service.CreateProductService;
import com.korit.pawsmarket.domain.product.service.ReadProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ReadProductService readProductService;
    private final ReadCategoryService readCategoryService;
    private final CreateProductService createProductService;
    private final ThreadPoolTaskScheduler taskScheduler;

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

    /**
     * 재고 변동이 발생한 후에 호출되어, 상품의 판매 상태를 업데이트하는 로직입니다.
     * - 재고가 0보다 크면 판매 상태를 "판매중(ON_SALE)"으로 설정합니다.
     * - 재고가 0이면 판매 상태를 "일시품절(OUT_OF_STOCK)"로 설정합니다.
     *
     * @param productId 상태를 확인하고 업데이트할 상품의 ID
     */
    public void checkSaleStatus(Long productId) {
        // 상품 ID를 사용하여 상품 정보를 조회 (상품이 없으면 예외 발생)
        Product product = readProductService.findByIdQuery(productId);

        // 상품의 현재 재고 수량 확인
        int stock = product.getStock();

        // 재고 상태에 따라 판매 상태 결정 (재고가 있으면 판매상태: 판매중, 재고가 0이라면 판매상태: 일시품절)
        SaleStatus status = (stock > 0) ? SaleStatus.ON_SALE : SaleStatus.OUT_OF_STOCK;

        // 현재 판매 상태와 비교하여 다른경우에만 상품의 판매 상태를 업데이트 (불필요한 상태 업데이트를 방지)
        if (product.getSaleStatus() != status) {
            product.updateSaleStatus(status);
            createProductService.save(product);
        }
    }

    // 상품 입고 주문
    public void scheduleStockArrival(Long productId, int quantity) {
        taskScheduler.schedule(
                () -> increaseStock(productId, quantity),
                // Instant.now().plus(3, ChronoUnit.HOURS)      // 입고 주문 후 3시간 뒤 increaseStock 메서드 실행
                Instant.now().plus(3, ChronoUnit.MINUTES)       // 테스트를 위해 3분 뒤 실행되도록 함
        );
    }

    // 상품 입고 => 재고 증가 로직
    public void  increaseStock(Long productId, int quantity) {
        log.info("Scheduled task started to increase stock for productId: {}, quantity: {}", productId, quantity);
        Product product = readProductService.findByIdQuery(productId);

        // 재고 업데이트 (현재 재고 + 입고된 수량)
        product.updateStock(product.getStock() + quantity);
        log.info("Stock updated for productId: {}. New stock: {}", productId, product.getStock());

        // 판매 상태 점검
        checkSaleStatus(productId);
        createProductService.save(product);
    }

    public void updateProduct(Long productId, UpdateProductReqDto reqDto) {
        Product product = readProductService.findByIdQuery(productId);
        Category category = readCategoryService.findById(reqDto.categoryId());

        product.updateProduct(reqDto, category);
        createProductService.save(product);
    }

    @Transactional(readOnly = true)
    public Page<GetLowStockProductRespDto> getLowStockProducts(
            int pageNo, int pageSize, String sortBy, String direction, int threshold
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(direction), sortBy));

        return readProductService.findAllByThreshold(pageable, threshold).map(GetLowStockProductRespDto::from);
    }
}
