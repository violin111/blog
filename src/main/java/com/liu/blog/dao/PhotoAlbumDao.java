package com.liu.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.entity.PhotoAlbum;
import com.liu.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoAlbumDao extends BaseMapper<PhotoAlbum> {
    /**
     * 查看后台相册列表
     * @param current .....页码
     * @param size .....大小
     * @param condition ....查询条件
     * @return
     */
    List<PhotoAlbumBackDTO> listPhotoAlbumBacks(@Param("current") long current, @Param("size") long size,@Param("condition") ConditionVO condition);


}
