package com.korit.pawsmarket.domain.order.facade;

import com.korit.pawsmarket.domain.OrderDetail.dto.req.CreateOrderDetailReqDto;
import com.korit.pawsmarket.domain.OrderDetail.dto.resp.CreateOrderDetailRespDto;
import com.korit.pawsmarket.domain.OrderDetail.entity.OrderDetail;
import com.korit.pawsmarket.domain.OrderDetail.facde.OrderDetailFacade;
import com.korit.pawsmarket.domain.order.dto.req.CreateOrderReqDto;
import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.order.service.CreateOrderService;
import com.korit.pawsmarket.domain.product.entity.Product;
import com.korit.pawsmarket.domain.product.entity.repository.ProductRepository;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.entity.repository.UserRepository;
import com.korit.pawsmarket.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CreateOrderService createOrderService;
    private final OrderDetailFacade orderDetailFacade;

    public Order createOrder(CreateOrderReqDto req) {

        //1. 유저 조회
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저 입니다."));

        //2. 주문 객체 생성
        Order order = req.toEntity(user);

        //3. 주문 상품 조회 (상품 정보가 db에 있는지 확인)
        List<Long> productIds = req.orderDetails().stream()
                .map(CreateOrderDetailReqDto -> CreateOrderDetailReqDto.productId())
                .toList();
        List<Product> products = productRepository.findByProductIds(productIds);

        if (products.size() != productIds.size()) {
            throw new NotFoundException("존재하지 않는 상품이 있습니다.");
        }

        //4. 주문 상세 리스트 생성
        List<CreateOrderDetailRespDto> orderDetails = req.orderDetails().stream()
                .map(createOrderDetailReqDto -> {
                    Product product = products.stream()
                            .filter(p -> p.getProductId().equals(createOrderDetailReqDto.productId()))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다. 상품 ID: " + createOrderDetailReqDto.productId()));

                    OrderDetail orderDetail = orderDetailFacade.createOrderDetail(order, product, createOrderDetailReqDto.quantity());

                    return CreateOrderDetailRespDto.from(orderDetail);
                })
                .toList();

        //6. 주문 저장
        createOrderService.createOrder(order);

        //7.주문 상세 저장
        return order;

    }

}
