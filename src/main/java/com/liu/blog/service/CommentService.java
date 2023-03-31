package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.CommentBackDTO;
import com.liu.blog.dto.CommentDTO;
import com.liu.blog.entity.Comment;
import com.liu.blog.vo.CommentVO;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.ReviewVO;

public interface CommentService extends IService<Comment> {
    /**
     * 查询后台评论
     *
     * @param condition 条件
     * @return 评论列表
     */
    PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition);

    /**
     * 审核评论
     * @param reviewVO
     */
    void updateCommentsReview(ReviewVO reviewVO);
}
