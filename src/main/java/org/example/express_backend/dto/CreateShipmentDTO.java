package org.example.express_backend.dto;

import lombok.Data;
import org.example.express_backend.entity.Shipment;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateShipmentDTO implements Serializable {
    private Integer origin;
    private Integer destination;
    private Integer customerId;
    private List<Integer> packageIds;
    private Integer type;
    private String payMethod;
}
