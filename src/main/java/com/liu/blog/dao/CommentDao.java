package com.liu.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.blog.dto.CommentBackDTO;
import com.liu.blog.dto.CommentDTO;
import com.liu.blog.dto.ReplyCountDTO;
import com.liu.blog.dto.ReplyDTO;
import com.liu.blog.entity.Comment;
import com.liu.blog.vo.CommentVO;
import com.liu.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends BaseMapper<Comment> {
    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);
    /**
     * 查看评论
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDTO> listComments(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVO commentVO);
    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);
    /**
     * 查询后台评论
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 评论集合
     */
    List<CommentBackDTO> listCommentBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);
    /**
     * 统计后台评论数量
     *
     * @param condition 条件
     * @return 评论数量
     */
    Integer countCommentDTO(@Param("condition") ConditionVO condition);


}
