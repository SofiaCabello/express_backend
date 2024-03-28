package org.example.express_backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
public class Logistics {
    private Integer id;
    private String name;
    private Integer parentId;
    private String level;
    private String districtCode;
    private String city;
    private String province;
    private String contactInfo;

    /**
     * 物流等级枚举，在MySQL中有 'province', 'city', 'district' 三种等级
     */
    @Getter
    private enum levelEnum {
        PROVINCE("province"),
        CITY("city"),
        DISTRICT("district");

        private final String level;

        levelEnum(String level) {
            this.level = level;
        }
    }
}
