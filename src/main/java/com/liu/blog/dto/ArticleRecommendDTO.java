package com.liu.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 推荐文章
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRecommendDTO {
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
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
