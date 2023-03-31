package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.ArchiveDTO;
import com.liu.blog.dto.ArticleBackDTO;
import com.liu.blog.dto.ArticleDTO;
import com.liu.blog.dto.ArticleHomeDTO;
import com.liu.blog.enums.FilePathEnum;
import com.liu.blog.service.ArticleService;
import com.liu.blog.strategy.context.ArticleImportStrategyContext;
import com.liu.blog.strategy.context.UploadStrategyContext;
import com.liu.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;

import static com.liu.blog.constant.OptTypeConst.*;

/**
 * 文章模块
 */
@Api(tags = "文章模块")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    @Autowired
    private ArticleImportStrategyContext articleImportStrategyContext;

    /**
     * 查看文章归档
     *
     * @return {@link Result<ArchiveDTO>} 文章归档列表
     */
    @ApiOperation(value = "查看文章归档")
    @GetMapping("/articles/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }

    /**
     * 查看首页文章
     *
     * @return {@link Result<ArticleHomeDTO>} 首页文章列表
     */
    @ApiOperation(value = "查看首页文章")
    @GetMapping("/articles")
    public Result<List<ArticleHomeDTO>> listArticles() {
        return Result.ok(articleService.listArticles());
    }

    /**
     * 上传文章图片
     * @param file
     * @return
     */
    @ApiOperation(value = "上传文章图片")
    @ApiImplicitParam(name = "file",value = "文章图片",required = true,dataType = "MultipartFile")
    @PostMapping("/admin/articles/images")
    public Result<String> saveArticleImages(MultipartFile file){
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }

    /**
     * 查看后台文章
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查看后台文章")
    @GetMapping("/admin/articles")
    public Result<PageResult<ArticleBackDTO>> listArticleBacks(ConditionVO conditionVO){
        return Result.ok(articleService.listArticleBacks(conditionVO));
    }

    /**
     * 添加或修改文章
     *
     * @param articleVO 文章信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改文章")
    @PostMapping("/admin/articles")
    public Result<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return Result.ok();
    }

    /**
     *恢复或删除文章
     * @param deleteVO
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "恢复或删除")
    @PutMapping("/admin/articles")
    public Result<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO){
        articleService.updateArticleDelete(deleteVO);
        return Result.ok();
    }

    /**
     * 删除文章
     *
     * @param articleIdList 文章id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "物理删除文章")
    @DeleteMapping("/admin/articles")
    public Result<?> deleteArticles(@RequestBody List<Integer> articleIdList) {
        articleService.deleteArticles(articleIdList);
        return Result.ok();
    }

    /**
     * 根据id查看后台文章
     *
     * @param articleId 文章id
     * @return {@link Result<ArticleVO>} 后台文章
     */
    @ApiOperation(value = "根据id查看后台文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/admin/articles/{articleId}")
    public Result<ArticleVO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleBackById(articleId));
    }

    /**
     * 修改文章置顶
     * @param articleVO
     * @return
     */
    @ApiOperation(value = "修改文章置顶")
    @PutMapping("/admin/articles/top")
    public Result<?> updateArticleTop(@Valid @RequestBody ArticleVO articleVO){
        articleService.updateArticleTop(articleVO);
        return  Result.ok();
    }

    /**
     * 导出文章
     *
     * @param articleIdList 文章id列表
     * @return {@link List<String>} 文件url列表
     */
    @ApiOperation(value = "导出文章")
    @ApiImplicitParam(name = "articleIdList", value = "文章id", required = true, dataType = "List<Integer>")
    @PostMapping("/admin/articles/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIdList) {
        return Result.ok(articleService.exportArticles(articleIdList));
    }

    /**
     * 导入文章
     * @param file
     * @param type
     * @return
     */
    @ApiOperation(value = "导入文章")
    @PostMapping("/admin/articles/import")
    public Result<?> importArticles(MultipartFile file,@RequestParam(required = false) String type){
        articleImportStrategyContext.importArticles(file, type);
        return Result.ok();
    }

}
