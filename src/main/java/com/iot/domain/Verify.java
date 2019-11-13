package com.iot.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 校验信息
 *
 * @author Nian Guowei
 */
@Data
@ApiModel(value = "校验")
public class Verify extends BaseEntity {
    @ApiModelProperty(value = "校验名称")
    private String name;
    @ApiModelProperty(value = "规则")
    private String rules;

}
