package com.nab.assignment.product.service;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<BrandDTO> getBrandList();
    List<ColorDTO> getColorList();
    List<TagDTO> getTagList();

    Page<ProductDTO> getProductPage(Pageable pageable);
}
