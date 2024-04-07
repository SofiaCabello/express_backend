package org.example.express_backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@Builder
public class PackageDTO {
    private Integer id;
    private String status;
}
