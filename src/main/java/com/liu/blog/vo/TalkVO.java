package com.liu.blog.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "说说对象")
public class TalkVO {
    /**
     * 说说id
     */
    @ApiModelProperty(name = "id",value = "说说id",dataType = "Integer")
    private Integer id;
    /**
     * 说说内容
     */
    @ApiModelProperty(name = "content",value = "说说内容",dataType = "String")
    @NotBlank(message = "说说内容不能为空")
    private String content;
    /**
     * 说说照片
     */
    @ApiModelProperty(name = "images",value = "说说照片",dataType = "String")
    private String images;
    /**
     * 是否置顶
     */
    @ApiModelProperty(name = "isTop",value = "置顶状态",dataType = "Integer")
    @NotNull(message = "置顶状态不能为空")
    private Integer isTop;
    /**
     * 说说状态 1：公开 2：私密
     */
    @ApiModelProperty(name = "status",value = "说说状态",dataType = "Integer")
    @NotNull(message = "说说状态不能为空")
    private Integer status;

    //Notnull 不能为null，但可以为empty，一般用在Integer类型的基本数据类型的非空校验上。
    // 而且被标注的字段则可以使用@size() @Max() @Min 对字段数值进行大小的控制


    //NotEmpty 不能null和empty 数组长度大于0 一般作用在集合或者数组上

    //NotBlank 只能作用在接收的String类型上，而且字符串中去除空格长度大于0
    //在controller层中接受前端传入的参数必须的加上@valid一起使用进行校验

}
