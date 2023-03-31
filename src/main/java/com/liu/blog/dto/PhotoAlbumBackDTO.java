package com.liu.blog.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumBackDTO {
    /**
     * 相册id
     */
    private Integer id;
    /**
     * 相册名字
     */
    private String albumName;
    /**
     *相册描述
     */
    private String albumDesc;
    /**
     * 相册封面
     */
    private String albumCover;
    /**
     * 照片数量
     */
    private Integer PhotoCount;
    /**
     * 状态 1：公开  2：私密
     */
    private Integer status;
}
