package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.TagBackDTO;
import com.liu.blog.dto.TalkBackDTO;
import com.liu.blog.entity.Talk;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.TalkVO;

import java.util.List;

public interface TalkService extends IService<Talk> {
     /**
      * 获取首页说说列表
      *
      * @return {@link List<String>} 说说列表
      */
     List<String> listHomeTalks();


     void saveOrUpdateTalk(TalkVO talkVO);

     PageResult<TalkBackDTO> listBackTalks(ConditionVO conditionVO);

     /**
      * 删除说说
      *
      * @param talkIdList 说说id列表
      */
     void deleteTalks(List<Integer> talkIdList);

}
