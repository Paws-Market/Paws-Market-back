package com.korit.pawsmarket.domain.product.service;

import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.entity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductService {

    private final ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }
}
