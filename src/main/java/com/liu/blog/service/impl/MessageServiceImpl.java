package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.MessageDao;
import com.liu.blog.dto.MessageBackDTO;
import com.liu.blog.entity.Message;
import com.liu.blog.service.MessageService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.liu.blog.constant.CommonConst.FALSE;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public PageResult<MessageBackDTO> listMessageBackDTO(ConditionVO conditionVO) {
        Page<Message> page = new Page<>(PageUtils.getCurrent(),PageUtils.getSize());
        Page<Message> messagePage = messageDao.selectPage(page, new LambdaQueryWrapper<Message>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Message::getNickname, conditionVO.getKeywords())
                .eq(Objects.nonNull(conditionVO.getIsReview()), Message::getIsReview, conditionVO.getIsReview())
                .orderByDesc(Message::getId));
        //转换成DTO
        List<MessageBackDTO> messageBackDTOList = BeanCopyUtils.copyList(messagePage.getRecords(), MessageBackDTO.class);
        return new PageResult<>(messageBackDTOList,(int)messagePage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMessagesReview(ReviewVO reviewVO) {
        //修改留言审核状态
        List<Message> messageList = reviewVO.getIdList().stream().map(item -> Message.builder()
                .id(item)
                .isReview(reviewVO.getIsReview())
                .build()).collect(Collectors.toList());
        this.updateBatchById(messageList);
    }

}
