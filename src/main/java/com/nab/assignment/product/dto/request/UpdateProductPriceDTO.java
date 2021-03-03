package com.nab.assignment.product.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UpdateProductPriceDTO implements Serializable {
    @NotNull
    private String id;

    @NotNull
    private Long price;
}
