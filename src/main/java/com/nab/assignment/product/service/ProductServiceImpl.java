package com.nab.assignment.product.service;

import com.nab.assignment.product.dto.BrandDTO;
import com.nab.assignment.product.dto.ColorDTO;
import com.nab.assignment.product.dto.ProductDTO;
import com.nab.assignment.product.dto.TagDTO;
import com.nab.assignment.product.jpa.specification.product.ProductSpecification;
import com.nab.assignment.product.jpa.specification.product.ProductSpecificationsBuilder;
import com.nab.assignment.product.jpa.specification.product.SearchCriteria;
import com.nab.assignment.product.model.*;
import com.nab.assignment.product.repository.*;
import com.nab.assignment.product.util.ProductUtils;
import com.nab.assignment.product.util.VNCharacterUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;
    private final ProductPriceTraceLogRepository productPriceTraceLogRepository;

    public ProductServiceImpl(BrandRepository brandRepository, ColorRepository colorRepository, TagRepository tagRepository, ProductRepository productRepository, ProductPriceTraceLogRepository productPriceTraceLogRepository) {
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.tagRepository = tagRepository;
        this.productRepository = productRepository;
        this.productPriceTraceLogRepository = productPriceTraceLogRepository;
    }

    @Override
    public List<BrandDTO> getBrandList() {
        List<Brand> brandList = brandRepository.findAll();

        if (CollectionUtils.isEmpty(brandList)) {
            return null;
        }

        return brandList.stream().map(o -> ProductUtils.convertToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<ColorDTO> getColorList() {
        List<Color> colorList = colorRepository.findAll();

        if (CollectionUtils.isEmpty(colorList)) {
            return null;
        }

        return colorList.stream().map(o -> ProductUtils.convertToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<TagDTO> getTagList() {
        List<Tag> tagList = tagRepository.findAll();

        if (CollectionUtils.isEmpty(tagList)) {
            return null;
        }

        return tagList.stream().map(o -> ProductUtils.convertToDTO(o)).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getProductPage(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(product -> ProductUtils.convertToDTO(product));
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

        return productPage.map(product -> ProductUtils.convertToDTO(product));
    }

    @Override
    @Transactional
    public void updatePrice(UUID id, Long price) {
        productRepository.updatePrice(id, price);
        ProductPriceTraceLog traceLog = new ProductPriceTraceLog(id, price);
        productPriceTraceLogRepository.save(traceLog);
    }

    @Override
    public ProductDTO getProductDetails(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.isPresent() ? ProductUtils.convertToDTO(optionalProduct.get()) : null;
    }

    @Override
    public Boolean validateProductQuantity(UUID id, Long quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            log.error("Not found product id: " + id.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found product id: " + id.toString());
        }

        if (quantity > 0 && quantity <= optionalProduct.get().getQuantity()) {
            return true;
        }

        return false;
    }

    @Override
    public Long getPrice(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            log.error("Not found product id: " + id.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found product id: " + id.toString());
        }

        return optionalProduct.get().getPrice();
    }
}
