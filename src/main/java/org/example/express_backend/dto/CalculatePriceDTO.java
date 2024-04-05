package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatePriceDTO {
    private Integer origin;
    private Integer destination;
    private Double weight;
    private String size;
    private Integer type;
}
