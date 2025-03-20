package com.korit.pawsmarket.domain.product.entity.repository;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.isDelete = false")
    Page<Product> findAllQuery(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.category.categoryType = :categoryType")
    Page<Product> findAllByCategoryQuery(Pageable pageable, @Param("categoryType") CategoryType categoryType);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.productId = :productId")
    Optional<Product> findByIdQuery(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.productId IN :productIds")
    List<Product> findByProductIds(@Param("productIds") List<Long> productIds);
}
