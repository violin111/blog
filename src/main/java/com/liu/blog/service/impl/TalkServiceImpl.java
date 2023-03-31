package com.liu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.TalkDao;
import com.liu.blog.dto.TagBackDTO;
import com.liu.blog.dto.TalkBackDTO;
import com.liu.blog.entity.Talk;
import com.liu.blog.service.TalkService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.CommonUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.util.UserUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.TalkVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TalkServiceImpl extends ServiceImpl<TalkDao, Talk> implements TalkService {
    @Autowired
    private TalkService talkService;
    @Autowired
    private TalkDao talkDao;

    @Override
    public List<String> listHomeTalks() {

        return null;
    }

    /**
     * 保存或者更新说说
     * @param talkVO
     */
    @Override
    public void saveOrUpdateTalk(TalkVO talkVO) {
        Talk talk = BeanCopyUtils.copyObject(talkVO, Talk.class);
        talk.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(talk);
    }

    /**
     * 查看后台说说
     * @param conditionVO
     * @return
     */
    @Override
    public PageResult<TalkBackDTO> listBackTalks(ConditionVO conditionVO) {
        //获取到说说数量
        Integer count = talkDao.selectCount(new LambdaQueryWrapper<Talk>().eq(Objects.nonNull(conditionVO.getStatus()), Talk::getStatus, conditionVO.getStatus()));
        if(count==0){
            return new PageResult<>();
        }
        //分页查询说说
        List<TalkBackDTO> talkBackDTOList = talkDao.listBackTalks(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        //将数据库中images存放在list中，因为前端将图片呈现出来将list中遍历一遍就行
        talkBackDTOList.forEach(item->{
            if(Objects.nonNull(item.getImages())){
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(),List.class),String.class));
            }
        });
        return new PageResult<>(talkBackDTOList,count);
    }


    @Override
    public void deleteTalks(List<Integer> talkIdList) {
        talkDao.deleteBatchIds(talkIdList);
    }


}
