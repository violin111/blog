package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.dto.PhotoBackDTO;
import com.liu.blog.entity.Photo;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.DeleteVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.PhotoVO;

import java.util.List;

public interface PhotoService extends IService<Photo> {
    /**
     * 根据相册id获取照片列表
     * @param conditionVO  条件
     * @return
     */
    PageResult<PhotoBackDTO> listPhotos(ConditionVO conditionVO);

    /**
     * 修改照片的状态值
     * @param deleteVO
     */
    void updatePhotoDelete(DeleteVO deleteVO);

    /**
     * 删除照片
     *
     * @param photoIdList 照片id列表
     */
    void deletePhotos(List<Integer> photoIdList);

    /**
     *保存图片
     * @param photoVO
     */
    void savePhotos(PhotoVO photoVO);

    /**
     * 移动照片
     * @param photoVO
     */
    void updatePhotosAlbum(PhotoVO photoVO);


}
