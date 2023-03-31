package com.liu.blog.controller;

import com.liu.blog.annotation.OptLog;
import com.liu.blog.dto.PhotoAlbumBackDTO;
import com.liu.blog.dto.PhotoAlbumDTO;
import com.liu.blog.enums.FilePathEnum;
import com.liu.blog.service.PhotoAlbumService;
import com.liu.blog.strategy.context.UploadStrategyContext;
import com.liu.blog.vo.ConditionVO;
import com.liu.blog.vo.PageResult;
import com.liu.blog.vo.PhotoAlbumVO;
import com.liu.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.liu.blog.constant.OptTypeConst.REMOVE;
import static com.liu.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

@Api(tags ="相册模块")
@RestController
public class PhotoAlbumController {
    @Autowired
    private PhotoAlbumService photoAlbumService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 查看后台相册列表
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查看后台相册列表")
    @GetMapping("/admin/photos/albums")
    public Result<PageResult<PhotoAlbumBackDTO>> listPhotoAlbumBacks(ConditionVO conditionVO){
        return Result.ok(photoAlbumService.listPhotoAlbumBacks(conditionVO));
    }

    /**
     * 保存或者更新相册
     * @param photoAlbumVO
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新相册")
    @PostMapping("/admin/photos/albums")
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO){
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.ok();
    }



    /**
     * 根据id逻辑删除相册
     * @param albumId
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据id逻辑删除")
    @ApiImplicitParam(name = "albumId",value = "相册id",required = true,dataType = "Integer")
    @DeleteMapping("/admin/photos/albums/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId){
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.ok();
    }


    /**
     * 上传相册封面
     * @param file
     * @return
     */
    @ApiOperation(value = "上传相册封面")
    @PostMapping("/admin/photos/albums/cover")
    @ApiImplicitParam(name = "file",value = "相册封面",required = true,dataType = "MultipartFile")
    public Result<String> savePhotoAlbumCover(MultipartFile file)  {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file,FilePathEnum.PHOTO.getPath()));
    }

    /**
     * 根据id获取后台相册信息
     * @param albumId
     * @return
     */
    @ApiOperation(value = "根据id获取后台相册信息")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @GetMapping("/admin/photos/albums/{albumId}/info")
    public Result<PhotoAlbumBackDTO> getPhotoAlbumBackById(@PathVariable Integer albumId){
        return Result.ok(photoAlbumService.getPhotoAlbumBackById(albumId));
    }

    /**
     * 获取后台相册列表信息
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表信息
     */
    @ApiOperation(value = "获取后台相册列表信息")
    @GetMapping("/admin/photos/albums/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.ok(photoAlbumService.listPhotoAlbumBackInfos());
    }

}
