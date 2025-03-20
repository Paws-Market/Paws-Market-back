package com.korit.pawsmarket.domain.product.entity.repository;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.enums.PetType;
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

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.petType IN :petTypes")
    Page<Product> findAllQuery(Pageable pageable, @Param("petTypes") List<PetType> petTypes);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.category.categoryType = :categoryType AND p.petType IN :petTypes")
    Page<Product> findAllByCategoryQuery(Pageable pageable, @Param("categoryType") CategoryType categoryType, @Param("petTypes") List<PetType> petTypes);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.productId = :productId")
    Optional<Product> findByIdQuery(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) AND p.petType IN :petTypes")
    // lower를 이용하여 대소문자 구분없이 검색이 가능하도록 함
    Page<Product> findAllBySearchQuery(Pageable pageable, @Param("search") String search, @Param("petTypes") List<PetType> petTypes);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.category.categoryType = :categoryType " +
            "AND LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) AND p.petType IN :petTypes")
    Page<Product> findAllByCategoryAndSearchQuery(Pageable pageable, @Param("categoryType") CategoryType categoryType, @Param("search") String search, @Param("petTypes") List<PetType> petTypes);

    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.stock <= :threshold")
    Page<Product> findAllByThreshold(Pageable pageable, @Param("threshold") int threshold);
}
