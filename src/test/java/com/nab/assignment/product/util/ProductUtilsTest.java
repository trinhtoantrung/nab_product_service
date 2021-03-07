package com.nab.assignment.product.util;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.model.Brand;
import com.nab.assignment.product.model.Color;
import com.nab.assignment.product.model.Product;
import com.nab.assignment.product.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductUtilsTest {

    @Test
    void convertBrandToDTO() {
        assertEquals(null, ProductUtils.convertBrandToDTO(null));

        Brand brand = new Brand("azh", "AZH Living");
        BrandDTO result = ProductUtils.convertBrandToDTO(brand);
        assertEquals("azh", result.getCode());
        assertEquals("AZH Living", result.getName());
    }

    @Test
    void convertColorToDTO() {
        assertEquals(null, ProductUtils.convertColorToDTO(null));

        Color color = new Color("red", "Red");
        ColorDTO result = ProductUtils.convertColorToDTO(color);
        assertEquals("red", result.getCode());
        assertEquals("Red", result.getName());
    }

    @Test
    void convertTagToDTO() {
        assertEquals(null, ProductUtils.convertTagToDTO(null));

        Tag tag = new Tag("new", "NEW");
        TagDTO result = ProductUtils.convertTagToDTO(tag);
        assertEquals("new", result.getCode());
        assertEquals("NEW", result.getName());
    }

    @Test
    void convertProductToDTO() {
        assertEquals(null, ProductUtils.convertProductToDTO(null));

        UUID id = UUID.randomUUID();
        Product product1 = new Product();
        product1.setId(id);
        product1.setModel("123-4-5");
        product1.setName("Armchair 1");
        product1.setPrice(1000L);
        product1.setQuantity(3L);

        ProductDTO result = ProductUtils.convertProductToDTO(product1);
        assertEquals(id, result.getId());
        assertEquals("123-4-5", result.getModel());
        assertEquals("Armchair 1", result.getName());
        assertEquals(1000L, result.getPrice());
        assertEquals(3L, result.getQuantity());
        assertEquals(null, result.getBrandDTOSet());
        assertEquals(null, result.getColorDTOSet());
        assertEquals(null, result.getTagDTOSet());

        Brand brand1 = new Brand("azh", "AZHOME Living");
        product1.getBrands().add(brand1);

        Color color1 = new Color("red", "Red");
        product1.getColors().add(color1);

        Tag tag1 = new Tag("new", "NEW");
        product1.getTags().add(tag1);

        result = ProductUtils.convertProductToDTO(product1);
        assertEquals(1, result.getBrandDTOSet().size());
        assertEquals(1, result.getColorDTOSet().size());
        assertEquals(1, result.getTagDTOSet().size());
    }

}