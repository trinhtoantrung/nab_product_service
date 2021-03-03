package com.nab.assignment.product.repository;

import com.nab.assignment.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
//    Boolean existsBrandByCode(String code);
}
