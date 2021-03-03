package com.nab.assignment.product.bean;

import com.nab.assignment.product.model.Brand;
import com.nab.assignment.product.model.Color;
import com.nab.assignment.product.model.Product;
import com.nab.assignment.product.model.Tag;
import com.nab.assignment.product.repository.BrandRepository;
import com.nab.assignment.product.repository.ColorRepository;
import com.nab.assignment.product.repository.ProductRepository;
import com.nab.assignment.product.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartupBean {
    private static final Logger log = LoggerFactory.getLogger(StartupBean.class);

    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;

    public StartupBean(BrandRepository brandRepository, ColorRepository colorRepository, TagRepository tagRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.tagRepository = tagRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        log.debug("--- StartupBean init ---");
        initSampleData();
    }

    private void initSampleData() {
        log.debug("--- Init sample data --- ");

        // brands: sonder-living, azhome-living, osho-living
        if (!brandRepository.existsById("sonder-living")) {
            Brand newBrand = new Brand("sonder-living", "Sonder Living");
            brandRepository.save(newBrand);
        }

        if (!brandRepository.existsById("azhome-living")) {
            Brand newBrand = new Brand("azhome-living", "AZHOME Living");
            brandRepository.save(newBrand);
        }

        if (!brandRepository.existsById("osho-living")) {
            Brand newBrand = new Brand("osho-living", "Osho Living");
            brandRepository.save(newBrand);
        }

        // colors: red, green, blue
        if (!colorRepository.existsById("red")) {
            Color newColor = new Color("red", "Red");
            colorRepository.save(newColor);
        }

        if (!colorRepository.existsById("green")) {
            Color newColor = new Color("green", "Green");
            colorRepository.save(newColor);
        }

        if (!colorRepository.existsById("blue")) {
            Color newColor = new Color("blue", "Blue");
            colorRepository.save(newColor);
        }

        // tags: new, hot, sale
        if (!tagRepository.existsById("new")) {
            Tag newTag = new Tag("new", "New");
            tagRepository.save(newTag);
        }

        if (!tagRepository.existsById("hot")) {
            Tag newTag = new Tag("hot", "Hot");
            tagRepository.save(newTag);
        }

        if (!tagRepository.existsById("sale")) {
            Tag newTag = new Tag("sale", "Sale");
            tagRepository.save(newTag);
        }

        // products => models 123-23-1, 392-43-2, 383-25-5
        if (!productRepository.existsByModel("123-23-1")) {
            Product newProduct = new Product("123-23-1", "Ross Armless Chair - Melinda Nubia Fabric");

            Brand brand = brandRepository.findById("sonder-living").get();
            newProduct.getBrands().add(brand);

            Color color = colorRepository.findById("red").get();
            newProduct.getColors().add(color);

            Tag tag = tagRepository.findById("hot").get();
            newProduct.getTags().add(tag);

            productRepository.save(newProduct);
        }

        if (!productRepository.existsByModel("392-43-2")) {
            Product newProduct = new Product("392-43-2", "Avalon Sofa - 3-Seat");

            Brand brand = brandRepository.findById("azhome-living").get();
            newProduct.getBrands().add(brand);

            Color color = colorRepository.findById("green").get();
            newProduct.getColors().add(color);

            Tag tag = tagRepository.findById("new").get();
            newProduct.getTags().add(tag);

            productRepository.save(newProduct);
        }

        if (!productRepository.existsByModel("383-25-5")) {
            Product newProduct = new Product("383-25-5", "Dawson Armless Chair - Melinda Nubia");

            Brand brand = brandRepository.findById("osho-living").get();
            newProduct.getBrands().add(brand);

            Color color = colorRepository.findById("blue").get();
            newProduct.getColors().add(color);

            Tag tag = tagRepository.findById("sale").get();
            newProduct.getTags().add(tag);

            productRepository.save(newProduct);
        }
    };
}
