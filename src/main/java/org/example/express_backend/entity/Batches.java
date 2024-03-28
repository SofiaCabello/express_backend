package org.example.express_backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Batches {
    private Integer id;
    private Timestamp createDate;
    private Integer origin;
    private Integer destination;
    private Integer responsible;
    private String status;
    private Integer vehicleId;

    @Getter
    public enum statusEnum {
        IN_TRANS("in_trans"),
        ARRIVE("arrive");

        private final String status;

        statusEnum(String status) {
            this.status = status;
        }
    }
}
