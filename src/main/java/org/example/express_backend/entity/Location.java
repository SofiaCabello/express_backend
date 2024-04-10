package org.example.express_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 位置信息实体类，用以追踪包裹位置
 */
@Data
@Builder
@AllArgsConstructor
public class Location {
    private Long id;
    private Point coordinate;
    private Timestamp time;
}
