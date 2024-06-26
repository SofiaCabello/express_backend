package org.example.express_backend.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;

import java.sql.Timestamp;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "batch", autoResultMap = true)
public class Batch {
    private Long id;
    private Timestamp createDate;
    private Long origin;
    private Long destination;
    private Long responsible;
    private String status;
    private Long vehicleId;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray packages;

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
