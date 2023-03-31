package com.liu.blog.strategy.impl;

import com.liu.blog.exception.BizException;
import com.liu.blog.strategy.UploadStrategy;
import com.liu.blog.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractUploadStrategyImpl implements UploadStrategy {
    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            //获取文件中的MD5值
            //首先将文件中的内容然后根据内容生成唯一该文件唯一的md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            //getOriginalFilename()获取文件的原始文件名
            String extName = FileUtils.getExtName(file.getOriginalFilename());
            //生成新的文件值
            String fileName=md5+extName;
            //判断oss仓库中是否存在这个文件
            if(!(exists(fileName))){
                //上传文件
                upload(path,fileName,file.getInputStream());
            }
            return getFileAccessUrl(path+fileName);
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String path) {
        try {
            //上传文件
            upload(path,fileName,inputStream);
            //返回文件访问路径
            return getFileAccessUrl(path+fileName);
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }

    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    public abstract Boolean exists(String path);

    /**
     * 上传
     * @param path
     * @param fileName
     * @param inputStream
     * @throws IOException
     */
    public abstract void upload(String path,String fileName,InputStream inputStream) throws IOException;

    /**
     *获取文件访问url
     * @param filePath
     */
    public abstract String  getFileAccessUrl(String filePath);
}
