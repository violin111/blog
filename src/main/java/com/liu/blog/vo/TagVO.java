package com.liu.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 标签VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {
    /**
     *id
     */
    @ApiModelProperty(name = "id",value = "标签id",dataType = "Integer")
    private Integer id;
    /**
     * 标签名称
     */
    @NotBlank
    @ApiModelProperty(name = "tagName",value = "标签文档",dataType = "String")
    private String tagName;
}
