package com.liu.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO {
    /**
     * 页面id
     */
    private Integer id;
    /**
     * 页面名
     */
    private String pageName;
    /**
     * 页面标签
     */
    private String pageLabel;
    /**
     * 页面封面
     */
    private String pageCover;
}
