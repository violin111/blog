package com.liu.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.blog.dto.TalkBackDTO;
import com.liu.blog.entity.Talk;
import com.liu.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalkDao extends BaseMapper<Talk> {
    /**
     * 查询后台说说
     * @param current
     * @param size
     * @param condition
     * @return
     */
    List<TalkBackDTO> listBackTalks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);
}
