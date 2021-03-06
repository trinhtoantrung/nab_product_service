package com.nab.assignment.product.service;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.model.*;
import com.nab.assignment.product.repository.*;
import com.nab.assignment.product.util.ProductUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private List<Product> productList;
    private Page<Product> productPage;

    @BeforeEach
    void setUp() {
        System.out.println("Setup - BeforeEach");
        productService = new ProductServiceImpl(brandRepository, colorRepository, tagRepository, productRepository, productPriceTraceLogRepository);

        productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Armchair 1");
        product1.setModel("1234-56");
        product1.setPrice(123000L);
        product1.setQuantity(3L);
        productList.add(product1);

        productPage = new PageImpl<>(productList);
    }

    @Test
    void getBrandList() {
        List<Brand> brandList1 = new ArrayList<>();
        Brand brand1 = new Brand("azh", "AZHOME Living");
        brandList1.add(brand1);
        List<BrandDTO> expectedResult1 = brandList1.stream().map(o -> ProductUtils.convertToDTO(o)).collect(Collectors.toList());

        when(brandRepository.findAll()).thenReturn(brandList1);
        assertEquals(expectedResult1, productService.getBrandList());

        when(brandRepository.findAll()).thenReturn(null);
        assertEquals(null, productService.getBrandList());
    }

    @Test
    void getColorList() {
        List<Color> colorList = new ArrayList<>();
        Color color1 = new Color("red", "Red");
        colorList.add(color1);
        List<ColorDTO> expectedResult = colorList.stream().map(o -> ProductUtils.convertToDTO(o)).collect(Collectors.toList());

        when(colorRepository.findAll()).thenReturn(null);
        assertEquals(null, productService.getColorList());

        when(colorRepository.findAll()).thenReturn(colorList);
        assertEquals(expectedResult, productService.getColorList());
    }

    @Test
    void getTagList() {
        List<Tag> tagList = new ArrayList<>();
        Tag tag1 = new Tag("new", "NEW");
        tagList.add(tag1);
        List<TagDTO> expectedResult = tagList.stream().map(o -> ProductUtils.convertToDTO(o)).collect(Collectors.toList());

        when(tagRepository.findAll()).thenReturn(null);
        assertEquals(null, productService.getTagList());

        when(tagRepository.findAll()).thenReturn(tagList);
        assertEquals(expectedResult, productService.getTagList());
    }

    @Test
    void getProductPage() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("dfd kj34 re");
        product1.setModel("128303-24");
        productList.add(product1);

        Page<Product> productPage = new PageImpl<>(productList);
        Page<ProductDTO> expectedResult = productPage.map(product -> ProductUtils.convertToDTO(product));

        when(productRepository.findAll(PageRequest.of(0, 20))).thenReturn(productPage);
        assertEquals(expectedResult, productService.getProductPage(PageRequest.of(0, 20)));
    }

    @Test
    void search() {
        String text = "drev iieor Se";
        Long minPrice = 0L;
        Long maxPrice = 4000000L;

        String[] brandArray = {"azh", "osh", "sonder"};
        Set<String> brands = new HashSet<>();
        CollectionUtils.addAll(brands, brandArray);

        String[] colorArray = {"red", "green", "blue"};
        Set<String> colors = new HashSet<>();
        CollectionUtils.addAll(colors, colorArray);

        String[] tagArray = {"new", "hot", "sale"};
        Set<String> tags = new HashSet<>();
        CollectionUtils.addAll(tags, tagArray);

        Page<ProductDTO> expectedResult = productPage.map(product -> ProductUtils.convertToDTO(product));

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productPage);

        assertEquals(expectedResult, productService.search(text, minPrice, maxPrice, brands, colors, tags, PageRequest.of(0, 20)));

    }

    @Test
    void updatePrice() {
        UUID id = UUID.randomUUID();
        Long price = 100000L;

        productService.updatePrice(id, price);
        verify(productRepository, times(1)).updatePrice(id, price);

        ArgumentCaptor<ProductPriceTraceLog> argument1 = ArgumentCaptor.forClass(ProductPriceTraceLog.class);

        verify(productPriceTraceLogRepository, times(1)).save(argument1.capture());
        assertEquals(id, argument1.getValue().getProductId());
        assertEquals(price, argument1.getValue().getPrice());
    }

    @Test
    void getProductDetails() {
        Product product = productList.get(0);
        ProductDTO expectedResult =  ProductUtils.convertToDTO(product);

        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));
        assertEquals(null, productService.getProductDetails(product.getId()));

        when(productRepository.findById((any(UUID.class)))).thenReturn(Optional.of(product));
        assertEquals(expectedResult, productService.getProductDetails(product.getId()));
    }

    @Test
    void validateProductQuantity() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productService.validateProductQuantity(id, 1L);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Not found product id: " + id.toString(), exception.getReason());

        Product product = productList.get(0);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        assertEquals(true, productService.validateProductQuantity(product.getId(), 2L));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        assertEquals(false, productService.validateProductQuantity(product.getId(), 4L));
    }

    @Test
    void getPrice() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productService.getPrice(id);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Not found product id: " + id.toString(), exception.getReason());

        Product product = productList.get(0);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        assertEquals(product.getPrice(), productService.getPrice(product.getId()));
    }
}