package com.iot.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 属性信息
 *
 * @author Nian Guowei
 */
@Data
@ApiModel(value = "属性")
public class Attribute extends BaseEntity {
    @ApiModelProperty(value = "模块ID")
    private Long modId;
    @ApiModelProperty(value = "code")
    private String code;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "分词标记")
    private String participle;
    @ApiModelProperty(value = "分词库名")
    private String analyzer;
    @ApiModelProperty(value = "默认值")
    private String defValue;
    @ApiModelProperty(value = "排序")
    private Integer sort;
}
