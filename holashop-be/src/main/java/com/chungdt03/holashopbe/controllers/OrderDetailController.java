package com.chungdt03.holashopbe.controllers;

import com.chungdt03.holashopbe.components.TranslateMessages;
import com.chungdt03.holashopbe.dtos.OrderDetailDTO;
import com.chungdt03.holashopbe.models.OrderDetail;
import com.chungdt03.holashopbe.models.User;
import com.chungdt03.holashopbe.responses.ApiResponse;
import com.chungdt03.holashopbe.responses.order_detail.OrderDetailResponse;
import com.chungdt03.holashopbe.services.OrderDetailService;
import com.chungdt03.holashopbe.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController extends TranslateMessages {

    private final OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .message(translate(MessageKeys.ERROR_MESSAGE))
                                .errors(errorMessages.stream().map(this::translate).toList()).build()
                );
            }

            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder().success(true)
                    .message(translate(MessageKeys.CREATE_ORDER_DETAILS_SUCCESS))
                    .payload(OrderDetailResponse.fromOrderDetail(newOrderDetail)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message(translate(MessageKeys.CREATE_ORDER_DETAILS_SUCCESS))
                            .error(e.getMessage()).build()
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws Exception {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(ApiResponse.builder().success(true).payload(OrderDetailResponse.fromOrderDetail(orderDetail)).build());
        // return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetailResponse> orderDetailResponses = orderDetailService.findByOrderId(orderId)
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(ApiResponse.builder().success(true).payload(orderDetailResponses).build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            @PathVariable("id") Long id
    ) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder().success(true).payload(OrderDetailResponse.fromOrderDetail(orderDetail)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder().error(e.getMessage()).build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok().body(ApiResponse.builder().success(true)
                    .message(translate(MessageKeys.MESSAGE_DELETE_SUCCESS)).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .error(e.getMessage())
                    .message(translate(MessageKeys.MESSAGE_DELETE_SUCCESS)).build());
        }
    }

}
