package com.nab.assignment.product.repository;

import com.nab.assignment.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Boolean existsByModel(String model);

    @Modifying
    @Query("UPDATE product SET price = :price WHERE id = :id")
    void updatePrice(@Param("id") UUID id, @Param("price") Long price);
}
