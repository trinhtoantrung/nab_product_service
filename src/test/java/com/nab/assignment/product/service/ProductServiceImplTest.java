package com.nab.assignment.product.service;

import com.nab.assignment.product.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductPriceTraceLogRepository productPriceTraceLogRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(brandRepository, colorRepository, tagRepository, productRepository, productPriceTraceLogRepository);
    }

    @Test
    void getBrandList() {
    }

    @Test
    void getColorList() {
    }

    @Test
    void getTagList() {
    }

    @Test
    void getProductPage() {
    }

    @Test
    void search() {
    }

    @Test
    void updatePrice() {
    }

    @Test
    void getProductDetails() {
    }

    @Test
    void validateProductQuantity() {
    }

    @Test
    void getPrice() {
    }
}