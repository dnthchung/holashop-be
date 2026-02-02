package com.chungdt03.holashopbe.repositories;

import com.chungdt03.holashopbe.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRespository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String couponCode);
}
