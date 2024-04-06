package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreatePackageDTO implements Serializable {
    private Integer shipmentId;
    private Integer receiverId;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;
    private Double weight;
    private String size;
}
