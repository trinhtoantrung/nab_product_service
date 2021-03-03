package com.nab.assignment.product.service;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.jpa.specification.product.ProductSpecification;
import com.nab.assignment.product.jpa.specification.product.ProductSpecificationsBuilder;
import com.nab.assignment.product.jpa.specification.product.SearchCriteria;
import com.nab.assignment.product.model.Brand;
import com.nab.assignment.product.model.Color;
import com.nab.assignment.product.model.Product;
import com.nab.assignment.product.model.Tag;
import com.nab.assignment.product.repository.BrandRepository;
import com.nab.assignment.product.repository.ColorRepository;
import com.nab.assignment.product.repository.ProductRepository;
import com.nab.assignment.product.repository.TagRepository;
import com.nab.assignment.product.util.ProductUtil;
import com.nab.assignment.product.util.VNCharacterUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(BrandRepository brandRepository, ColorRepository colorRepository, TagRepository tagRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.tagRepository = tagRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<BrandDTO> getBrandList() {
        List<Brand> brandList = brandRepository.findAll();

        if (CollectionUtils.isEmpty(brandList)) {
            return null;
        }

        return brandList.stream().map(o -> ProductUtil.convertToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<ColorDTO> getColorList() {
        List<Color> colorList = colorRepository.findAll();

        if (CollectionUtils.isEmpty(colorList)) {
            return null;
        }

        return colorList.stream().map(o -> ProductUtil.convertToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<TagDTO> getTagList() {
        List<Tag> tagList = tagRepository.findAll();

        if (CollectionUtils.isEmpty(tagList)) {
            return null;
        }

        return tagList.stream().map(o -> ProductUtil.convertToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getProductPage(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(product -> ProductUtil.convertToDTO(product));
    }

    @Override
    public Page<ProductDTO> search(String text, Long minPrice, Long maxPrice, Set<String> brands, Set<String> colors, Set<String> tags, Pageable pageable) {
        Specification searchSpecification;

        String searchString = StringUtils.isEmpty(text) ? text : StringUtils.lowerCase(VNCharacterUtils.removeAccent(text));

        // searchString
        ProductSpecificationsBuilder textSpecificationBuilder = new ProductSpecificationsBuilder();
        textSpecificationBuilder.with("searchString", ":", searchString);
        Specification<Product> textSpecifications = textSpecificationBuilder.build();
        searchSpecification = Specification.where(textSpecifications);

        // brands
        ProductSpecificationsBuilder brandSpecificationBuilder = new ProductSpecificationsBuilder();
        if (CollectionUtils.isNotEmpty(brands)) {
            for (String brandCode : brands) {
                brandSpecificationBuilder.with("brands", "IN", brandCode);
            }
            Specification<Product> brandSpecification = brandSpecificationBuilder.build();
            searchSpecification = searchSpecification.and(brandSpecification);
        }

        // colors
        ProductSpecificationsBuilder colorSpecificationBuilder = new ProductSpecificationsBuilder();
        if (CollectionUtils.isNotEmpty(colors)) {
            for (String colorCode : colors) {
                colorSpecificationBuilder.with("colors", "IN", colorCode);
            }
            Specification<Product> colorSpecification = colorSpecificationBuilder.build();
            searchSpecification = searchSpecification.and(colorSpecification);
        }

        // tags
        ProductSpecificationsBuilder tagSpecificationBuilder = new ProductSpecificationsBuilder();
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tagCode : tags) {
                tagSpecificationBuilder.with("tags", "IN", tagCode);
            }
            Specification<Product> tagSpecification = tagSpecificationBuilder.build();
            searchSpecification = searchSpecification.and(tagSpecification);
        }

        ProductSpecification minPriceSpecification = new ProductSpecification(new SearchCriteria("price", ">", minPrice));
        searchSpecification = searchSpecification.and(minPriceSpecification);

        ProductSpecification maxPriceSpecification = new ProductSpecification(new SearchCriteria("price", "<", maxPrice));
        searchSpecification = searchSpecification.and(maxPriceSpecification);

        Page<Product> productPage = productRepository.findAll(searchSpecification, pageable);

        return productPage.map(product -> ProductUtil.convertToDTO(product));
    }
}
