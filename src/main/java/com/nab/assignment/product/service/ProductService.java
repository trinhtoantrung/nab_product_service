package com.nab.assignment.product.service;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<BrandDTO> getBrandList();
    List<ColorDTO> getColorList();
    List<TagDTO> getTagList();

    Page<ProductDTO> getProductPage(Pageable pageable);
    Page<ProductDTO> search(String text,
                            Long minPrice,
                            Long maxPrice,
                            Set<String> brands,
                            Set<String> colors,
                            Set<String> tags,
                            Pageable pageable);
}
