package com.korit.pawsmarket.domain.product.facade;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.category.service.ReadCategoryService;
import com.korit.pawsmarket.domain.product.dto.resp.GetProductListRespDto;
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

    @Transactional(readOnly = true)
    public Page<GetProductListRespDto> getProductList(
            int pageNo, int pageSize, String sortBy, String direction, CategoryType categoryType
    ) {
        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(Sort.Direction.fromString(direction), sortBy));

        if (categoryType == null) {
            return readProductService.findAllQuery(pageable).map(GetProductListRespDto::from);
        }

        return readProductService.findAllByCategoryQuery(pageable, categoryType).map(GetProductListRespDto::from);
    }
}
