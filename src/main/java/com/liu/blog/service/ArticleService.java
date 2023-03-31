package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.ArchiveDTO;
import com.liu.blog.dto.ArticleBackDTO;
import com.liu.blog.dto.ArticleDTO;
import com.liu.blog.dto.ArticleHomeDTO;
import com.liu.blog.entity.Article;
import com.liu.blog.vo.ArticleVO;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.DeleteVO;
import com.liu.blog.vo.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleService extends IService<Article> {
    /**
     * 查询文章归档
     *
     * @return 文章归档
     */
    PageResult<ArchiveDTO> listArchives();
    /**
     *查询首页文章
     * @return  文章列表
     */
    List<ArticleHomeDTO> listArticles();
    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(@Param("articleId") Integer articleId);

    /**
     *添加或者修改文章
     * @param articleVO
     */
    void saveOrUpdateArticle(ArticleVO articleVO);

    /**
     *查询后台文章
     * @param conditionVO
     * @return
     */
    PageResult<ArticleBackDTO> listArticleBacks(ConditionVO conditionVO);

    /**
     *恢复或删除文章
     * @param deleteVO
     */
    void updateArticleDelete(DeleteVO deleteVO);

    /**
     * 导出文章
     * @param articleIdList
     * @return
     */
    List<String> exportArticles(List<Integer> articleIdList);

    /**
     * 修改文章置顶
     *
     * @param articleVO
     */
    void updateArticleTop(ArticleVO articleVO);
    /**
     * 根据id查看后台文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    ArticleVO getArticleBackById(Integer articleId);

    /**
     * 物理删除文章
     * @param articleIdList
     */
    void deleteArticles(List<Integer> articleIdList);
}
