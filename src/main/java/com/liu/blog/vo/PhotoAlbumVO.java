package com.liu.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "相册")
public class PhotoAlbumVO {
    /**
     * 相册id
     */
    @ApiModelProperty(name = "id",value = "相册id",dataType = "Integer")
    private Integer id;
    /**
     * 相册名称
     */
    @ApiModelProperty(name = "albumName",value = "相册名",dataType = "String")
    @NotBlank(message = "相册名字不能为空")
    private String albumName;
    /**
     * 相册描述
     */
    private String albumDesc;
    /**
     * 相册封面
     */
    @NotBlank(message = "相册封面不能为空")
    private String albumCover;
    /**
     * 相册状态
     */
    private Integer status;
}
