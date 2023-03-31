package com.liu.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 将photo实体类进行包装成PhotoBackDTO传给前端
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoBackDTO {
    /**
     *照片id
     */
    private Integer id;
    /**
     * 照片文件名
     */
    private String photoName;
    /**
     *照片描述
     */
    private String photoDesc;
    /**
     *照片地址
     */
    private String photoSrc;
}
