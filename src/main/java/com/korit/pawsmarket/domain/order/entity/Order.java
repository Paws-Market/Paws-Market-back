package com.korit.pawsmarket.domain.order.entity;

import com.korit.pawsmarket.domain.OrderDetail.entity.OrderDetail;
import com.korit.pawsmarket.domain.order.enums.OrderStatus;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price")
    private int totalPrice; //총 주문 금액

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus; //주문 상태(결제 완료, 배송 중 등)

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress; // 배송지 기본값: User.address

    @Column(name = "receiver_name", nullable = false)
    private String receiverName; // 수령인 기본값: User.nick

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails; // 주문 상세 목록

}
