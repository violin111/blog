package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.PhotoDao;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.dto.PhotoBackDTO;
import com.liu.blog.entity.Photo;
import com.liu.blog.entity.PhotoAlbum;
import com.liu.blog.service.PhotoAlbumService;
import com.liu.blog.service.PhotoService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.DeleteVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.liu.blog.constant.CommonConst.FALSE;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoDao, Photo> implements PhotoService {
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Override
    public PageResult<PhotoBackDTO> listPhotos(ConditionVO conditionVO) {
        //查询照片列表
        //分页条件
        Page<Photo> page = new Page<>(PageUtils.getCurrent(),PageUtils.getSize());
        Page<Photo> photoPage = photoDao.selectPage(page, new LambdaQueryWrapper<Photo>()
                .eq(Objects.nonNull(conditionVO.getAlbumId()), Photo::getAlbumId, conditionVO.getAlbumId())
                .eq(Photo::getIsDelete, conditionVO.getIsDelete())
                .orderByDesc(Photo::getId)
                .orderByDesc(Photo::getUpdateTime));
        //photoPage.getRecords()中Records的是查询出来的列表
        List<PhotoBackDTO> photoBackDTOS = BeanCopyUtils.copyList(photoPage.getRecords(), PhotoBackDTO.class);
        return new PageResult<>(photoBackDTOS,(int)photoPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePhotoDelete(DeleteVO deleteVO) {
        // 更新照片状态
        List<Photo> photoList = deleteVO.getIdList().stream().map(item -> Photo.builder()
                .id(item)
                .isDelete(deleteVO.getIsDelete())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
        // 若恢复照片所在的相册已删除，恢复相册
        if (deleteVO.getIsDelete().equals(FALSE)) {
            List<PhotoAlbum> photoAlbumList = photoDao.selectList(new LambdaQueryWrapper<Photo>()
                    .select(Photo::getAlbumId)
                    .in(Photo::getId, deleteVO.getIdList())
                    .groupBy(Photo::getAlbumId))
                    .stream()
                    .map(item -> PhotoAlbum.builder()
                            .id(item.getAlbumId())
                            .isDelete(FALSE)
                            .build())
                    .collect(Collectors.toList());
            photoAlbumService.updateBatchById(photoAlbumList);
        }
    }
    //这个是声明式事务   https://blog.csdn.net/csdnlaiyanqi/article/details/121478081
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePhotos(List<Integer> photoIdList) {
        photoDao.deleteBatchIds(photoIdList);
    }

    //保存图片到相册中
    @Override
    public void savePhotos(PhotoVO photoVO) {
        List<Photo> photoList = photoVO.getPhotoUrlList().stream().map(item -> Photo.builder()
                .albumId(photoVO.getAlbumId())
                .photoName(IdWorker.getIdStr())
                .photoSrc(item)
                .build())
                .collect(Collectors.toList());
        this.saveBatch(photoList);
    }
    //移动照片到其他相册
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePhotosAlbum(PhotoVO photoVO) {
        List<Photo> collect = photoVO.getPhotoIdList().stream().map(item -> Photo.builder()
                .id(item)
                .albumId(photoVO.getAlbumId())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(collect);
    }


}
