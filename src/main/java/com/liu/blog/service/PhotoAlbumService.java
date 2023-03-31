package com.liu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.blog.dao.PhotoDao;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.dto.PhotoAlbumDTO;
import com.liu.blog.entity.PhotoAlbum;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.PhotoAlbumVO;

import java.util.List;

public interface PhotoAlbumService extends IService<PhotoAlbum> {
    /**
     * 查看后台相册列表
     *
     * @param conditionVO
     * @return
     */
    PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVO conditionVO);

    /**
     * 保存或者更新修改
     *
     * @param photoAlbumVO
     */
    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    /**
     * 根据封面id删除相册
     *
     * @param album
     */
    void deletePhotoAlbumById(Integer album);

    /**
     * 根据id查看相册的信息
     *
     * @param albumId
     * @return
     */
    PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId);

    /**
     * 查看相册中照片的信息
     * @return
     */
    List<PhotoAlbumDTO>  listPhotoAlbumBackInfos();
}
