package com.nab.assignment.product.jpa.specification.product;

import com.nab.assignment.product.model.Brand;
import com.nab.assignment.product.model.Color;
import com.nab.assignment.product.model.Product;
import com.nab.assignment.product.model.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Objects;

public class ProductSpecification implements Specification<Product> {
    private SearchCriteria criteria;

    public ProductSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (!Objects.isNull(criteria.getValue())) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (!Objects.isNull(criteria.getValue())) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (Objects.isNull(criteria.getValue())) {
                return null;
            }

            return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");

//            if (root.get(criteria.getKey()).getJavaType() == String.class) {
//                return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
//            } else {
//                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
//            }
        } else if (criteria.getOperation().equalsIgnoreCase("IN")) {
            if (Objects.isNull(criteria.getValue())) {
                return null;
            }

            if ("brands".equals(criteria.getKey())) {
                SetJoin<Product, Brand> brandSetJoin = root.joinSet("brands");
                query.distinct(true);
                return criteriaBuilder.equal(brandSetJoin.get("code"), criteria.getValue());
            }

            if ("colors".equals(criteria.getKey())) {
                SetJoin<Product, Color> colorSetJoin = root.joinSet("colors");
                query.distinct(true);
                return criteriaBuilder.equal(colorSetJoin.get("code"), criteria.getValue());
            }

            if ("tags".equals(criteria.getKey())) {
                SetJoin<Product, Tag> brandSetJoin = root.joinSet("tags");
                query.distinct(true);
                return criteriaBuilder.equal(brandSetJoin.get("code"), criteria.getValue());
            }
        }

        return null;
    }
}
