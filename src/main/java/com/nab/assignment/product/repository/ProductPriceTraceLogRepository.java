package com.nab.assignment.product.repository;

import com.nab.assignment.product.model.ProductPriceTraceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductPriceTraceLogRepository extends JpaRepository<ProductPriceTraceLog, UUID> {
}
