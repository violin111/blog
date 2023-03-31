package com.liu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.blog.dao.PhotoAlbumDao;
import com.liu.blog.dao.PhotoDao;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.dto.PhotoAlbumDTO;
import com.liu.blog.entity.Page;
import com.liu.blog.entity.Photo;
import com.liu.blog.entity.PhotoAlbum;
import com.liu.blog.exception.BizException;
import com.liu.blog.service.PhotoAlbumService;
import com.liu.blog.service.PhotoService;
import com.liu.blog.util.BeanCopyUtils;
import com.liu.blog.util.PageUtils;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.PhotoAlbumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.liu.blog.constant.CommonConst.FALSE;
import static com.liu.blog.constant.CommonConst.TRUE;

@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumDao, PhotoAlbum>  implements PhotoAlbumService {
    @Autowired
    private PhotoAlbumDao photoAlbumDao;
    @Autowired
    private PhotoDao photoDao;

    /**
     * 查看后台相册
     * @param conditionVO
     * @return
     */
    @Override
    public PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVO conditionVO) {
        //查询相册数量
        Integer count = photoAlbumDao.selectCount(new LambdaQueryWrapper<PhotoAlbum>()
                .like(Objects.nonNull(conditionVO.getKeywords()),
                        PhotoAlbum::getAlbumName, conditionVO.getKeywords())
                .eq(PhotoAlbum::getIsDelete, FALSE));
        if (count==0){
            return new PageResult<>();
        }
        //查询相册信息
        List<PhotoAlbumBackDTO> photoAlbumBackDTOS =
                photoAlbumDao.listPhotoAlbumBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        return new PageResult<>(photoAlbumBackDTOS,count);
    }

    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        //查看相册名是否存在
        PhotoAlbum album = photoAlbumDao.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                //这个select是相当于数据库语句select语句
                .select(PhotoAlbum::getId)
                .eq(PhotoAlbum::getAlbumName, photoAlbumVO.getAlbumName()));
        if(Objects.nonNull(album)&&!(album.getId().equals(photoAlbumVO.getId()))){
            throw new BizException("相册名已存在");
        }
        //将photoAlbumVO装换成photoAlbum对象
        PhotoAlbum photoAlbum = BeanCopyUtils.copyObject(photoAlbumVO, PhotoAlbum.class);
        this.saveOrUpdate(photoAlbum);
    }

    @Override
    public void deletePhotoAlbumById(Integer albumId) {
        //查询照片数量
        Integer count = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        if(count>0){
            //将数据库中的两个表中的逻辑删除改为TRUE
            //这样照片转到回收站之中之后若需要恢复则可以通过改变isDelete恢复相册
            photoAlbumDao.updateById(PhotoAlbum.builder()
                    .id(albumId)
                    .isDelete(TRUE)
                    .build());
            photoDao.update(new Photo(),new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getIsDelete,TRUE)
                    .eq(Photo::getAlbumId,albumId));
        }else {
            //若相册中不存在照片则直接删除
            photoAlbumDao.deleteById(albumId);
        }
    }

    @Override
    public PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId) {
        //先讲相册的信息筛选出来
        PhotoAlbum photoAlbum = photoAlbumDao.selectById(albumId);
        //将相册中照片的信息筛选出来
        Integer count = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getIsDelete,FALSE));
        PhotoAlbumBackDTO photoAlbumBackDTO = BeanCopyUtils.copyObject(photoAlbum, PhotoAlbumBackDTO.class);
        photoAlbumBackDTO.setPhotoCount(count);
        return photoAlbumBackDTO;
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumBackInfos() {
        List<PhotoAlbum> photoAlbums = photoAlbumDao.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getIsDelete, FALSE));
        return BeanCopyUtils.copyList(photoAlbums,PhotoAlbumDTO.class);
    }
}
