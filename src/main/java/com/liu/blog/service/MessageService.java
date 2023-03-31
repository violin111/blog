package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.MessageBackDTO;
import com.liu.blog.entity.Message;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.ReviewVO;

import java.util.List;

public interface MessageService extends IService<Message> {
    /**
     * 查询后台留言列表
     * @param conditionVO
     * @return
     */
    PageResult<MessageBackDTO> listMessageBackDTO(ConditionVO conditionVO);

    /**
     * 审核留言
     * @param reviewVO
     */
    void updateMessagesReview(ReviewVO reviewVO);


}
