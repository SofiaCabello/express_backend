package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO implements Serializable {
    private Timestamp createDate;
    private Long origin;
    private Long destination;
    private String status;
}
