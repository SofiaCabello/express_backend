package org.example.express_backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * shipments 实体类，用于描述运单信息
 */
@Data
@ToString
public class Shipments {
    private Integer id;
    private Timestamp createTime;
    private Integer origin;
    private Integer destination;
    private Double price;
    private String status;
    private Integer customerId;

    @Getter
    private enum statusEnum {
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
