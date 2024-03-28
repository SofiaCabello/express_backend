package org.example.express_backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Packages {
    private Integer id;
    private Integer receiverId;
    private Integer receiverName;
    private Integer receiverPhone;
    private Integer receiverAddress;
    private Timestamp signDate;
    private String status;
    private Integer shipmentId;
    private Integer batchId;

    @Getter
    private enum statusEnum {
        PENDING("pending"),
        PROCESSING("processing"),
        IN_TRANSIT("in_transit"),
        DELIVERING("delivering"),
        SIGNED("signed"),
        CANCELLED("cancelled");

        private final String status;

        statusEnum(String status) {
            this.status = status;
        }
    }
}
