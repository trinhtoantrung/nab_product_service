package com.nab.assignment.product.bean;

import com.nab.assignment.product.model.Brand;
import com.nab.assignment.product.model.Color;
import com.nab.assignment.product.model.Product;
import com.nab.assignment.product.model.Tag;
import com.nab.assignment.product.repository.BrandRepository;
import com.nab.assignment.product.repository.ColorRepository;
import com.nab.assignment.product.repository.ProductRepository;
import com.nab.assignment.product.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartupBeanTest {

    @InjectMocks
    private StartupBean startupBean;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        startupBean = new StartupBean(brandRepository, colorRepository, tagRepository, productRepository);
    }

    @Test
    void init() {

        // branch 1
        ArgumentCaptor<Brand> argumentCaptor = ArgumentCaptor.forClass(Brand.class);
        when(brandRepository.existsById("sonder-living")).thenReturn(false);
        when(brandRepository.existsById("azhome-living")).thenReturn(false);
        when(brandRepository.existsById("osho-living")).thenReturn(false);

        ArgumentCaptor<Color> argumentCaptor1 = ArgumentCaptor.forClass(Color.class);
        when(colorRepository.existsById("red")).thenReturn(false);
        when(colorRepository.existsById("green")).thenReturn(false);
        when(colorRepository.existsById("blue")).thenReturn(false);

        ArgumentCaptor<Tag> argumentCaptor2 = ArgumentCaptor.forClass(Tag.class);
        when(tagRepository.existsById("hot")).thenReturn(false);
        when(tagRepository.existsById("new")).thenReturn(false);
        when(tagRepository.existsById("sale")).thenReturn(false);

        // products => models 123-23-1, 392-43-2, 383-25-5
        ArgumentCaptor<Product> argumentCaptor3 = ArgumentCaptor.forClass(Product.class);
        when(productRepository.existsByModel("123-23-1")).thenReturn(false);
        when(productRepository.existsByModel("392-43-2")).thenReturn(false);
        when(productRepository.existsByModel("383-25-5")).thenReturn(false);

        Brand newBrand1 = new Brand("sonder-living", "Sonder Living");
        Brand newBrand2 = new Brand("azhome-living", "AZHOME Living");
        Brand newBrand3 = new Brand("osho-living", "Osho Living");
        when(brandRepository.findById("sonder-living")).thenReturn(Optional.of(newBrand1));
        when(brandRepository.findById("azhome-living")).thenReturn(Optional.of(newBrand2));
        when(brandRepository.findById("osho-living")).thenReturn(Optional.of(newBrand3));

        Color newColor1 = new Color("red", "red");
        Color newColor2 = new Color("green", "green");
        Color newColor3 = new Color("blue", "blue");
        when(colorRepository.findById("red")).thenReturn(Optional.of(newColor1));
        when(colorRepository.findById("green")).thenReturn(Optional.of(newColor2));
        when(colorRepository.findById("blue")).thenReturn(Optional.of(newColor3));

        Tag newTag1 = new Tag("hot", "HOT");
        Tag newTag2 = new Tag("new", "NEW");
        Tag newTag3 = new Tag("sale", "SALE");
        when(tagRepository.findById("hot")).thenReturn(Optional.of(newTag1));
        when(tagRepository.findById("new")).thenReturn(Optional.of(newTag2));
        when(tagRepository.findById("sale")).thenReturn(Optional.of(newTag3));

        startupBean.init();
        verify(brandRepository, times(3)).save(argumentCaptor.capture());
        assertEquals("sonder-living", argumentCaptor.getAllValues().get(0).getCode());
        assertEquals("azhome-living", argumentCaptor.getAllValues().get(1).getCode());
        assertEquals("osho-living", argumentCaptor.getAllValues().get(2).getCode());

        verify(colorRepository, times(3)).save(argumentCaptor1.capture());
        assertEquals("red", argumentCaptor1.getAllValues().get(0).getCode());
        assertEquals("green", argumentCaptor1.getAllValues().get(1).getCode());
        assertEquals("blue", argumentCaptor1.getAllValues().get(2).getCode());

        verify(tagRepository, times(3)).save(argumentCaptor2.capture());
        assertEquals("new", argumentCaptor2.getAllValues().get(0).getCode());
        assertEquals("hot", argumentCaptor2.getAllValues().get(1).getCode());
        assertEquals("sale", argumentCaptor2.getAllValues().get(2).getCode());

        verify(productRepository, times(3)).save((argumentCaptor3.capture()));
        assertEquals("123-23-1", argumentCaptor3.getAllValues().get(0).getModel());
        assertEquals("392-43-2", argumentCaptor3.getAllValues().get(1).getModel());
        assertEquals("383-25-5", argumentCaptor3.getAllValues().get(2).getModel());

        // branch 2
        when(brandRepository.existsById("sonder-living")).thenReturn(true);
        when(brandRepository.existsById("azhome-living")).thenReturn(true);
        when(brandRepository.existsById("osho-living")).thenReturn(true);

        when(colorRepository.existsById("red")).thenReturn(true);
        when(colorRepository.existsById("green")).thenReturn(true);
        when(colorRepository.existsById("blue")).thenReturn(true);

        when(tagRepository.existsById("hot")).thenReturn(true);
        when(tagRepository.existsById("new")).thenReturn(true);
        when(tagRepository.existsById("sale")).thenReturn(true);

        // products => models 123-23-1, 392-43-2, 383-25-5
        when(productRepository.existsByModel("123-23-1")).thenReturn(true);
        when(productRepository.existsByModel("392-43-2")).thenReturn(true);
        when(productRepository.existsByModel("383-25-5")).thenReturn(true);

        startupBean.init();
    }
}