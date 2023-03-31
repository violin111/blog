package com.liu.blog.controller;


import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.dto.PhotoBackDTO;
import com.liu.blog.service.PhotoService;
import com.liu.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;
import static com.liu.blog.constant.OptTypeConst.UPDATE;

/**
 *照片控制器
 */
@Api(tags = "照片模块")
@RestController
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    /**
     *获取后天照片列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "根据相册id获取照片列表")
    @GetMapping("/admin/photos")
    public Result<PageResult<PhotoBackDTO>> listPhotos(ConditionVO conditionVO){
        return Result.ok(photoService.listPhotos(conditionVO));
    }

    @ApiOperation(value = "保存图片")
    @PostMapping("/admin/photos")
    public Result<?> savePhotos(@Valid @RequestBody PhotoVO photoVO){
        photoService.savePhotos(photoVO);
        return Result.ok();
    }
    /**
     * 修改照片的状态
     * @param
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value ="删除照片")
    @DeleteMapping("/admin/photos")
    //如果参数时放在请求体中，application/json传入后台的话，那么后台要用@RequestBody才能接收到；
    //             如果不是放在请求体中的话，那么后台接收前台传过来的参数时，要用@RequestParam来接收，或
    //             则形参前 什么也不写也能接收。这个里面接受的参数是在请求体中
    public Result<?> deletePhotos(@RequestBody List<Integer> photoIdList){
        photoService.deletePhotos(photoIdList);
        return Result.ok();
    }

    /**
     *更新照片的删除状态
     * @param deleteVO
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新照片状态")
    @PutMapping("/admin/photos/delete")
    public Result<?> updatePhotoDelete(@Valid @RequestBody DeleteVO deleteVO){
        photoService.updatePhotoDelete(deleteVO);
        return Result.ok();
    }

    /**
     * 移动照片
     * @param photoVO
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "移动照片")
    @PutMapping("/admin/photos/album")
    public Result<?> updatePhotosAlbum(@Valid @RequestBody PhotoVO photoVO){
        photoService.updatePhotosAlbum(photoVO);
        return Result.ok();
    }
}


