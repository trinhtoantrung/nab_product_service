package com.nab.assignment.product.controller;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.dto.request.UpdateProductPriceDTO;
import com.nab.assignment.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private List<BrandDTO> brandDTOList = new ArrayList<>();
    private List<TagDTO> tagDTOList = new ArrayList<>();
    private List<ColorDTO> colorDTOList = new ArrayList<>();
    private Page<ProductDTO> productDTOPage;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setCode("azh");
        brandDTO.setName("AZHOME Living");
        brandDTOList.add(brandDTO);

        TagDTO tagDTO = new TagDTO();
        tagDTO.setCode("new");
        tagDTO.setName("NEW");
        tagDTOList.add(tagDTO);

        ColorDTO colorDTO = new ColorDTO();
        colorDTO.setCode("red");
        colorDTO.setName("RED");
        colorDTOList.add(colorDTO);

        List<ProductDTO> productDTOList = new ArrayList<>();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(UUID.randomUUID());
        productDTO.setPrice(1000000L);
        productDTO.setName("Armchair 1");
        productDTO.setModel("123-42-4");
        productDTO.setQuantity(3L);
        productDTOList.add(productDTO);
        productDTOPage = new PageImpl<>(productDTOList);
    }

    @Test
    void getBrandList() {
        when(productService.getBrandList()).thenReturn(brandDTOList);
        assertEquals(brandDTOList, productController.getBrandList());
    }

    @Test
    void getColorList() {
        when(productService.getColorList()).thenReturn(colorDTOList);
        assertEquals(colorDTOList, productController.getColorList());
    }

    @Test
    void getTagList() {
        when(productService.getTagList()).thenReturn(tagDTOList);
        assertEquals(tagDTOList, productController.getTagList());
    }

    @Test
    void getProductPage() {
        when(productService.getProductPage(any(Pageable.class))).thenReturn(productDTOPage);
        assertEquals(productDTOPage, productController.getProductPage(PageRequest.of(0, 20)));
    }

    @Test
    void search() {
        when(productService.search(anyString(), anyLong(), anyLong(), anySet(), anySet(), anySet(), any(Pageable.class)))
                .thenReturn(productDTOPage);
        assertEquals(productDTOPage, productController.search("", 0L, 0L, new HashSet<>(),new HashSet<>(),new HashSet<>(), PageRequest.of(0, 20)));
    }

    @Test
    void updatePrice() {
        UpdateProductPriceDTO dto = new UpdateProductPriceDTO();
        dto.setId(UUID.randomUUID().toString());
        dto.setPrice(10L);
        productController.updatePrice(dto);
        verify(productService, times(1)).updatePrice(UUID.fromString(dto.getId()), dto.getPrice());
    }

    @Test
    void getProductDetails() {
        ProductDTO expectedResult = productDTOPage.getContent().get(0);
        when(productService.getProductDetails(expectedResult.getId())).thenReturn(expectedResult);
        assertEquals(expectedResult, productController.getProductDetails(expectedResult.getId().toString()));
    }

    @Test
    void validateQuantity() {
        UUID id = productDTOPage.getContent().get(0).getId();
        Long qty = 3L;
        when(productService.validateProductQuantity(id, qty)).thenReturn(true);
        assertEquals(true, productController.validateQuantity(id.toString(), qty));

        qty = 2L;
        when(productService.validateProductQuantity(id, qty)).thenReturn(false);
        assertEquals(false, productController.validateQuantity(id.toString(), qty));
    }

    @Test
    void getPrice() {
        UUID id = UUID.randomUUID();
        Long price = 1000L;
        when(productService.getPrice(id)).thenReturn(price);
        assertEquals(price, productController.getPrice(id.toString()));
    }
}