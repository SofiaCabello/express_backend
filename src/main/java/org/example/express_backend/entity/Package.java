package org.example.express_backend.entity;

import lombok.*;

import java.sql.Timestamp;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Package {
    private Integer id;
    private Integer receiverId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Timestamp signDate;
    private String status;
    private Integer shipmentId;
    private Integer batchId;
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
