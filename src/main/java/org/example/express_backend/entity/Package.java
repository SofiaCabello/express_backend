package org.example.express_backend.entity;

import lombok.*;

import java.sql.Timestamp;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Package {
    private Long id;
    private Long receiverId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Timestamp signDate;
    private Timestamp createDate;
    private String status;
    private Long shipmentId;
    private Long batchId;
    private Double weight;
    private String size;

    @Getter
    public enum statusEnum {
        PENDING("pending"),
        PICKED_UP("picked_up"),
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
