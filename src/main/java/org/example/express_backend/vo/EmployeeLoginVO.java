package org.example.express_backend.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {

    @ApiModelProperty("主键值")
    private Long id;

    @ApiModelProperty("邮箱")
    private String eamil;

    //用户名
    @ApiModelProperty("用户名")
    private String name;

    //手机号
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("jwt令牌")
    private String token;

    private String serveAt;

    private Long logisticId;

}
