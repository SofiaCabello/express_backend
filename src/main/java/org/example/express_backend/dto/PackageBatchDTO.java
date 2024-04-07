package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageBatchDTO {
    private List<Integer> PackageIds;
    private Integer BatchId;
}
