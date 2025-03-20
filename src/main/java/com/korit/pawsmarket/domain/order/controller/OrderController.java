package com.korit.pawsmarket.domain.order.controller;

import com.korit.pawsmarket.domain.order.dto.req.CreateOrderReqDto;
import com.korit.pawsmarket.domain.order.facade.OrderFacade;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Order", description = "주문 API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createOrder (@Valid @RequestBody CreateOrderReqDto req) {
        orderFacade.createOrder(req);
        return ApiResponse.generateResp(Status.CREATE, "새로운 주문이 추가되었습니다.", null);
    }
}
