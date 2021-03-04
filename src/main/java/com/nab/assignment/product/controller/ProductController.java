package com.nab.assignment.product.controller;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.dto.request.UpdateProductPriceDTO;
import com.nab.assignment.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @GetMapping(path = "/search")
    public Page<ProductDTO> search(@Nullable @RequestParam("text") String text,
                                   @Nullable @RequestParam("minPrice") Long minPrice,
                                   @Nullable @RequestParam("maxPrice") Long maxPrice,
                                   @Nullable @RequestParam("brands") Set<String> brands,
                                   @Nullable @RequestParam("colors") Set<String> colors,
                                   @Nullable @RequestParam("tags") Set<String> tags,
                                   Pageable pageable) {
        return productService.search(text, minPrice, maxPrice, brands, colors, tags, pageable);
    }

    @PostMapping(path = "/update-price")
    public void updatePrice(@Valid @RequestBody UpdateProductPriceDTO dto) {
        productService.updatePrice(UUID.fromString(dto.getId()), dto.getPrice());
    }

    @GetMapping(path = "/id/{id}")
    public ProductDTO getProductDetails(@PathVariable("id") String id) {
        return productService.getProductDetails(UUID.fromString(id));
    }

    @GetMapping(path = "/validate-quantity")
    public Boolean validateQuantity(@RequestParam("id") String id, @RequestParam("quantity") Long quantity) {
        return productService.validateProductQuantity(UUID.fromString(id), quantity);
    }

    @GetMapping(path = "/price")
    public Long getPrice(@RequestParam("id") String id) {
        return productService.getPrice(UUID.fromString(id));
    }
}
