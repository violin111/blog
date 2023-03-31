package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.CommentDao;
import com.liu.blog.dto.CommentBackDTO;
import com.liu.blog.entity.Comment;
import com.liu.blog.service.CommentService;
import com.liu.blog.service.RedisService;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;




@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private RedisService redisService;

    @Override
    public PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO conditionVO) {
        // 统计后台评论量
        Integer count = commentDao.countCommentDTO(conditionVO);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        return new PageResult<>(commentBackDTOList, count);
    }

    @Transactional
    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // 修改评论审核状态
        List<Comment> commentList = reviewVO.getIdList().stream().map(item -> Comment.builder()
                .id(item)
                .isReview(reviewVO.getIsReview())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }
}
