package com.korit.pawsmarket.domain.product.service;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.entity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAllQuery(Pageable pageable) {
        return productRepository.findAllQuery(pageable);
    }

    public Page<Product> findAllByCategoryQuery(Pageable pageable, CategoryType categoryType) {
        return productRepository.findAllByCategoryQuery(pageable, categoryType);
    }
}
