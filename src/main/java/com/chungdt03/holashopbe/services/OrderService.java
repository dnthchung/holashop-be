package com.chungdt03.holashopbe.services;

import com.chungdt03.holashopbe.dtos.OrderDTO;
import com.chungdt03.holashopbe.models.Order;
import com.chungdt03.holashopbe.responses.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrderById(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> findByUserId(Long userId);

    Page<OrderResponse> findByKeyword(String keyword, Pageable pageable);
}
