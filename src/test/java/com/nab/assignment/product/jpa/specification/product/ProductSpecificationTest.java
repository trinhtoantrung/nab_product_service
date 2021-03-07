package com.nab.assignment.product.jpa.specification.product;

import com.nab.assignment.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductSpecificationTest {

    @InjectMocks
    private ProductSpecification productSpecification;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Root<Product> productRoot;

    @Mock
    private CriteriaQuery<Product> query;

    @Mock
    private SetJoin setJoin;

    private SearchCriteria searchCriteria;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(productSpecification, "criteria", searchCriteria);

    }

    @Test
    void toPredicate() {
        searchCriteria = new SearchCriteria("price", ">", 1000L);
        productSpecification = new ProductSpecification(searchCriteria);
        productSpecification.toPredicate(productRoot, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).greaterThanOrEqualTo(productRoot.get(searchCriteria.getKey()), searchCriteria.getValue().toString());

        searchCriteria = new SearchCriteria("price", ">", null);
        productSpecification = new ProductSpecification(searchCriteria);
        assertEquals(null, productSpecification.toPredicate(productRoot, query, criteriaBuilder));

        searchCriteria = new SearchCriteria("price", "<", 1000L);
        productSpecification = new ProductSpecification(searchCriteria);
        productSpecification.toPredicate(productRoot, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).lessThanOrEqualTo(productRoot.get(searchCriteria.getKey()), searchCriteria.getValue().toString());

        searchCriteria = new SearchCriteria("price", "<", null);
        productSpecification = new ProductSpecification(searchCriteria);
        assertEquals(null, productSpecification.toPredicate(productRoot, query, criteriaBuilder));

        searchCriteria = new SearchCriteria("searchString", ":", null);
        productSpecification = new ProductSpecification(searchCriteria);
        assertEquals(null, productSpecification.toPredicate(productRoot, query, criteriaBuilder));

        searchCriteria = new SearchCriteria("searchString", ":", "Armchair 1");
        productSpecification = new ProductSpecification(searchCriteria);
        productSpecification.toPredicate(productRoot, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).like(productRoot.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");

        searchCriteria = new SearchCriteria("tag", "IN", null);
        productSpecification = new ProductSpecification(searchCriteria);
        assertEquals(null, productSpecification.toPredicate(productRoot, query, criteriaBuilder));

        searchCriteria = new SearchCriteria("unknownKey", "IN", "unknownValue");
        productSpecification = new ProductSpecification(searchCriteria);
        assertEquals(null, productSpecification.toPredicate(productRoot, query, criteriaBuilder));

        Set<String> brands = new HashSet<>();
        brands.add("azh");
        searchCriteria = new SearchCriteria("brands", "IN", brands);
        when(productRoot.joinSet("brands")).thenReturn(setJoin);
        productSpecification = new ProductSpecification(searchCriteria);
        productSpecification.toPredicate(productRoot, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).equal(setJoin.get("code"), searchCriteria.getValue());

        Set<String> colors = new HashSet<>();
        colors.add("red");
        searchCriteria = new SearchCriteria("colors", "IN", colors);
        when(productRoot.joinSet("colors")).thenReturn(setJoin);
        productSpecification = new ProductSpecification(searchCriteria);
        productSpecification.toPredicate(productRoot, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).equal(setJoin.get("code"), searchCriteria.getValue());

        Set<String> tags = new HashSet<>();
        tags.add("new");
        searchCriteria = new SearchCriteria("tags", "IN", tags);
        when(productRoot.joinSet("tags")).thenReturn(setJoin);
        productSpecification = new ProductSpecification(searchCriteria);
        productSpecification.toPredicate(productRoot, query, criteriaBuilder);
        verify(criteriaBuilder, times(1)).equal(setJoin.get("code"), searchCriteria.getValue());

        searchCriteria = new SearchCriteria("unknownKey", "unknownOperation", "unknownValue");
        productSpecification = new ProductSpecification(searchCriteria);
        assertEquals(null, productSpecification.toPredicate(productRoot, query, criteriaBuilder));
    }


}