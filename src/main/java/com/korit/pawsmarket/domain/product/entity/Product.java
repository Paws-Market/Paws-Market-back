package com.korit.pawsmarket.domain.product.entity;

import com.korit.pawsmarket.domain.category.entity.Category;
import com.korit.pawsmarket.domain.product.dto.req.UpdateProductReqDto;
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
    private String productName;             // 상품명

    private int price;                      // 가격

    // 이미지 문자열 길이가 길어서 늘려줌
    @Column(length = 1000, name = "image_url")
    private String imageUrl;                // 대표 이미지

    private int stock;                      // 재고

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private SaleStatus saleStatus;          // 판매상태

    @Column(length = 1000, name = "description_image_url1")
    private String descriptionImageUrl1;    // 상세설명 이미지1

    @Column(length = 1000, name = "description_image_url2")
    private String descriptionImageUrl2;    // 상세설명 이미지2

    @Column(length = 1000, name = "description_image_url3")
    private String descriptionImageUrl3;    // 상세설명 이미지3

    @Column(name = "sales_quantity")
    private int salesQuantity;              // 판매 수량

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type")
    private PetType petType;                // 펫 타입

    @Column(name = "discount_rate")
    private int discountRate;               // 할인율

    public void decreaseStock(int quantity) {
        if(this.stock < quantity) {
            throw new IllegalArgumentException("재고 부족: 현재 재고 " + this.stock + ", 요청 수량 " + quantity);
        }
        this.stock -= quantity; // 재고 감소
        this.salesQuantity += quantity; // 판매 수량 증가
    }

    public void updateSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public void updateStock(int quantity) {
        this.stock = quantity;
    }

    public void updateProduct(UpdateProductReqDto reqDto, Category category) {
        this.category = category;
        this.productName = reqDto.productName();
        this.price = reqDto.price();
        this.imageUrl = reqDto.imageUrl();
        this.saleStatus = reqDto.saleStatus();
        this.descriptionImageUrl1 = reqDto.descriptionImageUrl1();
        this.descriptionImageUrl2 = reqDto.descriptionImageUrl2();
        this.descriptionImageUrl3 = reqDto.descriptionImageUrl3();
        this.petType = reqDto.petType();
        this.discountRate = reqDto.discountRate();
    }
}

