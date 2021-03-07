package com.nab.assignment.product.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductPriceDTO implements Serializable {
    @NotNull
    private String id;

    @NotNull
    private Long price;
}
