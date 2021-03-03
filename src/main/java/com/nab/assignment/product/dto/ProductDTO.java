package com.nab.assignment.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private UUID id;
    private String model;
    private String name;
    private Long price;

    @JsonProperty("brands")
    private Set<BrandDTO> brandDTOSet;

    @JsonProperty("colors")
    private Set<ColorDTO> colorDTOSet;

    @JsonProperty("tags")
    private Set<TagDTO> tagDTOSet;
}
