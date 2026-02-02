package com.chungdt03.holashopbe.services;

import com.chungdt03.holashopbe.dtos.OrderDetailDTO;
import com.chungdt03.holashopbe.models.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetail getOrderDetail(Long id) throws Exception;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);

    void deleteOrderDetail(Long id);

    List<OrderDetail> findByOrderId(Long orderId);
}
