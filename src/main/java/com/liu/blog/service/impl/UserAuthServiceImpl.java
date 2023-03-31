package com.liu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.constant.CommonConst;
import com.liu.blog.dao.UserAuthDao;
import com.liu.blog.dao.UserInfoDao;
import com.liu.blog.dao.UserRoleDao;
import com.liu.blog.dto.*;
import com.liu.blog.entity.UserAuth;
import com.liu.blog.entity.UserInfo;
import com.liu.blog.entity.UserRole;
import com.liu.blog.enums.LoginTypeEnum;
import com.liu.blog.enums.RoleEnum;
import com.liu.blog.exception.BizException;
import com.liu.blog.service.BlogInfoService;
import com.liu.blog.service.RedisService;
import com.liu.blog.service.UserAuthService;
import com.liu.blog.strategy.context.SocialLoginStrategyContext;
import com.liu.blog.util.PageUtils;
import com.liu.blog.util.UserUtils;
import com.liu.blog.vo.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.liu.blog.constant.RedisPrefixConst.USER_AREA;
import static com.liu.blog.constant.RedisPrefixConst.VISITOR_AREA;
import static com.liu.blog.enums.UserAreaTypeEnum.getUserAreaType;


/**
 * 用户账号服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth> implements UserAuthService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SocialLoginStrategyContext socialLoginStrategyContext;


    @Override
    public void sendCode(String username) {

    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(conditionVO.getType()))){
            case USER:
                //查询注册用户区域分布
                Object userArea = redisService.get(USER_AREA);
                if(Objects.nonNull(userArea)){
                    userAreaDTOList = JSON.parseObject(userArea.toString(),List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                //查询游客区域分布
                Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                if(Objects.nonNull(visitorArea)){
                    userAreaDTOList=visitorArea.entrySet().stream()
                            .map(item->UserAreaDTO.builder()
                                 .name(item.getKey())
                                 .value(Long.valueOf(item.getValue().toString()))
                                 .build())
                             .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }
        return userAreaDTOList;
    }

    @Override
    public void register(UserVO user) {

    }

    @Override
    public UserInfoDTO qqLogin(QQLoginVO qqLoginVO) {
        return null;
    }

    @Override
    public UserInfoDTO weiboLogin(WeiboLoginVO weiboLoginVO) {
        return null;
    }

    @Override
    public void updatePassword(UserVO user) {

    }

    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {
        // 查询旧密码是否正确
        UserAuth user = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getId, UserUtils.getLoginUser().getId()));
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVO.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(UserUtils.getLoginUser().getId())
                    .password(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userAuthDao.updateById(userAuth);
        } else {
            throw new BizException("旧密码不正确");
        }
    }

    @Override
    public PageResult<UserBackDTO> listUserBackDTO(ConditionVO condition) {
        // 获取后台用户数量
        Integer count = userAuthDao.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 获取后台用户列表
        List<UserBackDTO> userBackDTOList = userAuthDao.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(userBackDTOList, count);
    }
}
