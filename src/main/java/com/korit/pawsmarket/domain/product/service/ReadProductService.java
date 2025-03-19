package com.korit.pawsmarket.domain.product.service;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.entity.repository.ProductRepository;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReadProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAllQuery(Pageable pageable, List<PetType> petTypes) {
        return productRepository.findAllQuery(pageable, petTypes);
    }

    public Page<Product> findAllByCategoryQuery(Pageable pageable, CategoryType categoryType, List<PetType> petTypes) {
        return productRepository.findAllByCategoryQuery(pageable, categoryType, petTypes);
    }

    public Product findByIdQuery(Long productId) {
        return productRepository.findByIdQuery(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품을 찾을 수 없습니다."));
    }

    public Page<Product> findAllBySearchQuery(Pageable pageable, String search, List<PetType> petTypes) {
        return productRepository.findAllBySearchQuery(pageable, search, petTypes);
    }

    public Page<Product> findAllByCategoryAndSearchQuery(Pageable pageable, CategoryType categoryType, String search, List<PetType> petTypes) {
        return productRepository.findAllByCategoryAndSearchQuery(pageable, categoryType, search, petTypes);
    }
}
