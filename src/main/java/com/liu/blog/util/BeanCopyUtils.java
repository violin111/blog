package com.liu.blog.util;

import java.util.ArrayList;
import java.util.List;

public class BeanCopyUtils {
    /**
     * 复制对象
     *
     * @param source 源
     * @param target 目标
     * @return {@link T}
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static <T,S> List<T> copyList(List<S> source, Class<T> target){
        List<T> list=new ArrayList<>();
        if(source !=null&&source.size()>0){
            for(Object obj:source){
                list.add(BeanCopyUtils.copyObject(obj,target));
            }
        }
        return list;
    }
}
