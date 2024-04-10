package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatePriceDTO {
    private Integer origin; // 出发地编号
    private Integer destination;    // 目的地编号
    private Double weight;  // 包裹重量，单位kg
    private String size;    // 包裹尺寸，如 20,30,40 单位cm
    private Integer type;   // 包裹类型，0为标快，1为特快
}
