package com.nab.assignment.product.controller;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/brand/list")
    public List<BrandDTO> getBrandList() {
        return productService.getBrandList();
    }

    @GetMapping(path = "/color/list")
    public List<ColorDTO> getColorList() {
        return productService.getColorList();
    }

    @GetMapping(path = "/tag/list")
    public List<TagDTO> getTagList() {
        return productService.getTagList();
    }

    @GetMapping(path = "/page")
    public Page<ProductDTO> getProductPage(Pageable pageable) {
        return productService.getProductPage(pageable);
    }
}
