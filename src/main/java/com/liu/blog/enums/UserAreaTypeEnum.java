package com.liu.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户区域类型枚举
 */
@Getter
@AllArgsConstructor
public enum UserAreaTypeEnum {
    /**
     * 用户
     */
    USER(1, "用户"),
    /**
     * 游客
     */
    VISITOR(2, "游客");
    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     *得到用户的类型
     * @param type
     * @return
     */
    public static UserAreaTypeEnum getUserAreaType(Integer type){
        for (UserAreaTypeEnum value:UserAreaTypeEnum.values()) {
            if(value.getType().equals(type)){
                return value;
            }
        }
        return  null;
    }
}
