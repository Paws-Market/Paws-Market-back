package com.korit.pawsmarket.domain.OrderDetail.entity;

import com.korit.pawsmarket.domain.OrderDetail.enums.ShippingStatus;
import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; //주문 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 원가(상품 가격)

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price; //원가 (상품 가격)

    @Column( name = "discount_rate", nullable = false)
    @Builder.Default
    private int discountRate = 0; // 할인율 (주문 당시 할인율을 저장)

    @Column(name = "final_price", nullable = false)
    private int finalPrice; //할인 갸격 (price - (price * discountRate / 100))

    @Column(name = "total_price", nullable = false)
    private int totalPrice; //최종 가격 (finalPrice * quantity)

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status", nullable = false)
    private ShippingStatus shippingStatus; // 배송 상태(준비중, 배송중, 배송완료)

}
