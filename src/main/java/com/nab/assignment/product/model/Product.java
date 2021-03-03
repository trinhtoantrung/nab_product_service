package com.nab.assignment.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nab.assignment.product.util.VNCharacterUtils;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "product")
public class Product extends AbstractAuditingEntity {
    private static final Logger log = LoggerFactory.getLogger(Product.class);

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.StandardRandomStrategy"
                    )
            }
    )
    @Column(name = "id")
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String model;

    private Long price = 0L;

    @Column(name = "search_string")
    @JsonProperty("search_string")
    private String searchString = "";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_brand",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_code"))
    private Set<Brand> brands = new HashSet<>();

    // colors
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_color",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_code"))
    private Set<Color> colors = new HashSet<>();

    // tags
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_code"))
    private Set<Tag> tags = new HashSet<>();

    public Product(String model, String name) {
        this.name = name;
        this.model = model;
    }

    @PrePersist
    public void onPrePersist() {
        String searchString = name + " " + model;
        this.searchString = StringUtils.lowerCase(VNCharacterUtils.removeAccent(searchString));
        log.debug("Product onPrePersist - update searchString: {}", this.searchString);
    }
}
