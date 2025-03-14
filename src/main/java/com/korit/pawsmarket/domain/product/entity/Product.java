package com.korit.pawsmarket.domain.product.entity;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.domain.product.enums.SaleStatus;
import com.korit.pawsmarket.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_name")
    private String productName;

    private int price;

    @Column(name = "image_url")
    private String imageUrl;

    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private SaleStatus saleStatus;

    @Column(name = "description_image_url1")
    private String descriptionImageUrl1;

    @Column(name = "description_image_url2")
    private String descriptionImageUrl2;

    @Column(name = "description_image_url3")
    private String descriptionImageUrl3;

    @Column(name = "sales_quantity")
    private int salesQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type")
    private PetType petType;

    @Column(name = "discount_rate")
    private int discountRate;
}
