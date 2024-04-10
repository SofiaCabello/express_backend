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
    private List<Long> PackageIds; // 要添加的包裹id
    private Long BatchId; // 要添加到的批次id
}
