package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.CommentBackDTO;
import com.liu.blog.service.CommentService;
import com.liu.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;
import static com.liu.blog.constant.OptTypeConst.UPDATE;

@Api(tags = "评论模块")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 查询后台评论
     *
     * @param conditionVO 条件
     * @return {@link Result<CommentBackDTO>} 后台评论
     */
    @ApiOperation(value = "查询后台评论")
    @GetMapping("/admin/comments")
    public Result<PageResult<CommentBackDTO>> listCommentBackDTO(ConditionVO conditionVO){
        return Result.ok(commentService.listCommentBackDTO(conditionVO));
    }

    /**
     *删除评论
     * @param commentIdList
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除评论")
    @DeleteMapping("/admin/comments")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList){
        commentService.removeByIds(commentIdList);
        return Result.ok();
    }


    /**
     * 审核评论
     *
     * @param reviewVO 审核信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核评论")
    @PutMapping("/admin/comments/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.ok();
    }
}
