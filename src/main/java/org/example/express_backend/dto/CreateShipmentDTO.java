package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateShipmentDTO implements Serializable {
    private Integer origin; // 出发地编号
    private Integer destination;    // 目的地编号
    private Integer customerId; // 客户ID
//    private List<Integer> packageIds;   // 可用性存疑
    private Integer type;   // 0: 标快，1: 特快
    private String payMethod;   // "cod_pending": 货到付款，"pending": 预先支付
}
