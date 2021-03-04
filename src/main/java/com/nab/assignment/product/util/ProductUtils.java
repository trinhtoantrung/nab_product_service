package com.nab.assignment.product.util;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.model.Brand;
import com.nab.assignment.product.model.Color;
import com.nab.assignment.product.model.Product;
import com.nab.assignment.product.model.Tag;
import org.apache.commons.collections4.CollectionUtils;

import java.util.stream.Collectors;

public class ProductUtils {
    public static BrandDTO convertToDTO(Brand brand) {
        if (brand == null) {
            return null;
        }

        BrandDTO dto = new BrandDTO();
        dto.setCode(brand.getCode());
        dto.setName(brand.getName());

        return dto;
    }

    public static ColorDTO convertToDTO(Color color) {
        if (color == null) {
            return null;
        }

        ColorDTO dto = new ColorDTO();
        dto.setCode(color.getCode());
        dto.setName(color.getName());

        return dto;
    }

    public static TagDTO convertToDTO(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagDTO dto = new TagDTO();
        dto.setCode(tag.getCode());
        dto.setName(tag.getName());

        return dto;
    }

    public static ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setModel(product.getModel());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        if (CollectionUtils.isNotEmpty(product.getBrands())) {
            dto.setBrandDTOSet(
                    product.getBrands().stream().map(brand -> convertToDTO(brand)).collect(Collectors.toSet())
            );
        }

        if (CollectionUtils.isNotEmpty(product.getColors())) {
            dto.setColorDTOSet(
                    product.getColors().stream().map(color -> convertToDTO(color)).collect(Collectors.toSet())
            );
        }

        if (CollectionUtils.isNotEmpty(product.getTags())) {
            dto.setTagDTOSet(
                    product.getTags().stream().map(tag -> convertToDTO(tag)).collect(Collectors.toSet())
            );
        }

        return dto;
    }
}
