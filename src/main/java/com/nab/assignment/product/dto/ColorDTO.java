package com.nab.assignment.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ColorDTO implements Serializable {
    private String code;
    private String name;
}
