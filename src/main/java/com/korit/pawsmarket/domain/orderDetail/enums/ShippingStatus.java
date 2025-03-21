package com.korit.pawsmarket.domain.orderDetail.enums;

public enum ShippingStatus {
    READY, //배송 준비 중 (결제 대기)
    SHIPPED, //배송 중 (입금 완료)
    DELIVERED, //배송 완료 (3일 후)
    CANCELED, //주문 취소 됨
}
