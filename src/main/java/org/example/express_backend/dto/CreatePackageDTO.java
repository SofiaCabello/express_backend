package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreatePackageDTO implements Serializable {
    private Long shipmentId; // 运单id
    private String receiverEmail;    // 收件人邮箱
    private String receiverName;    // 收件人姓名
    private String receiverAddress; // 收件人地址
    private String receiverPhone;   // 收件人电话
    private Double weight;  // 包裹重量，单位kg
    private String size;    // 包裹尺寸，如 20,30,40 单位cm
}
