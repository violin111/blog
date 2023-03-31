package com.liu.blog.strategy;


import org.springframework.web.multipart.MultipartFile;

/**
 *文章导入策略
 */
public interface ArticleImportStrategy {
    /**
     *导入文章
     * @param file
     */
    void importArticles(MultipartFile file);
}
