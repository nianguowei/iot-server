package com.iot.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模块信息
 *
 * @author Nian Guowei
 */
@Data
@ApiModel(value = "模块")
public class Module extends BaseEntity {
    @ApiModelProperty(value = "模块名称")
    private String name;
    @ApiModelProperty(value = "父ID")
    private Long pId;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "排序")
    private Integer sort;
}
