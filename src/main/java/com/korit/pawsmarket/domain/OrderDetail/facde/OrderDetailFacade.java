package com.korit.pawsmarket.domain.OrderDetail.facde;

import com.korit.pawsmarket.domain.OrderDetail.entity.OrderDetail;
import com.korit.pawsmarket.domain.OrderDetail.entity.repository.OrderDetailRepository;
import com.korit.pawsmarket.domain.OrderDetail.enums.ShippingStatus;
import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OrderDetailFacade {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail createOrderDetail(Order order, Product product, int quantity) {

        //재고 차감 로직
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고 부족: 상품 ID " + product.getProductId() +", 남은 재고" + product.getStock());
        }
        product.decreaseStock(quantity);
        // 최종 가격 계산
        int originalPrice = product.getPrice(); // 원래 가격
        int discountRate = product.getDiscountRate(); // 할인율
        int discountedPrice = originalPrice - (originalPrice * discountRate / 100); // 할인 적용된 가격
        int totalPrice = discountedPrice * quantity; // 전체 가격

        // 할인율 계산된 OrderDetail 객체 생성
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .price(originalPrice) // 원래 가격 저장
                .discountRate(discountRate) // 할인율 저장
                .finalPrice(discountedPrice) // 할인 적용된 개당 가격
                .totalPrice(totalPrice) // 총 가격 저장
                .shippingStatus(ShippingStatus.READY) // 배송 상태 기본값 설정
                .build();

        return orderDetailRepository.save(orderDetail);
    }
}
