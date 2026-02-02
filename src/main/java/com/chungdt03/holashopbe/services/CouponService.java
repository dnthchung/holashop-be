package com.chungdt03.holashopbe.services;

public interface CouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}
