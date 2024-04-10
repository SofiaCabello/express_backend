package org.example.express_backend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * shipments 实体类，用于描述运单信息
 */
@Data
@ToString
@Builder
public class Shipment {
    private Long id;
    private Timestamp createDate;
    private Long origin;
    private Long destination;
    private Double price;
    private String status;
    private Long customerId;
    private Integer type;

    @Getter
    public enum statusEnum {
        PENDING("pending"),
        COD_PENDING("cod_pending"),
        PAID("paid"),
        CANCELLED("cancelled");

        private final String status;

        statusEnum(String status) {
            this.status = status;
        }
    }

}
