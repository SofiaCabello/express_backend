package org.example.express_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Point {
    private Double longitude; // 经度
    private Double latitude; // 纬度

    public String makePoint() {
        return String.format("POINT(%f %f)", latitude, longitude);
    }
}