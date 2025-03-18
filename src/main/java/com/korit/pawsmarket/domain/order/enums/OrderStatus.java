package com.korit.pawsmarket.domain.order.enums;

public enum OrderStatus {
    PAYMENT_PENDING, //결제 대기
    PAYMENT_COMPLETED, //결제 완료
    PAYMENT_FAILED, //결제 실패
    PREPARING_SIPMENT, //배송 준비중
    SHIPPED, //배송 중
    DELIVERED, //배송 완료
    CANCELED //주문 취소
}
