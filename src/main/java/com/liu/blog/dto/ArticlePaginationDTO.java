package com.liu.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章上下篇
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePaginationDTO {
    /**
     * id
     */
    private int id;
    /**
     *文章缩略图
     */
    private String articleCover;
    /**
     * 标题
     */
    private String articleTitle;
}
