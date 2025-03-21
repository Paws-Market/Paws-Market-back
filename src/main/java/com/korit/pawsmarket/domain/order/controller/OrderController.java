package com.korit.pawsmarket.domain.order.controller;

import com.korit.pawsmarket.domain.category.enums.CategoryType;
import com.korit.pawsmarket.domain.order.dto.req.CreateOrderReqDto;
import com.korit.pawsmarket.domain.order.dto.resp.GetOrderListRespDto;
import com.korit.pawsmarket.domain.order.entity.Order;
import com.korit.pawsmarket.domain.order.facade.OrderFacade;
import com.korit.pawsmarket.domain.product.enums.PetType;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "주문 목록 전체 조회", description = "현재 로그인한 사용자의 주문 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetOrderListRespDto>>> getOrderList(
            //@RequestParam(name = "userId") Long userId, // 요청한 사용자 ID
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        List<GetOrderListRespDto> orderList = orderFacade.getOrderList(pageNo, pageSize, sortBy, direction);
        return ApiResponse.generateResp(Status.SUCCESS, "주문 목록 조회 성공", orderList);
    }

}
