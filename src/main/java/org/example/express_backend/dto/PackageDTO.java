package org.example.express_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackageDTO {
    private Integer id;
    private String status;
}
