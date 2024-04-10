package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.Shipment;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ShipmentQueryResultDTO implements Serializable {
    private Shipment shipment;
    private List<Package> packages;
}
