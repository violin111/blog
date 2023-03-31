package com.liu.blog.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface UploadStrategy {
    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
    String uploadFile(MultipartFile file,String path);

    /**
     * 上传文件
     * @param fileName
     * @param inputStream
     * @param path
     * @return
     */
    String uploadFile(String fileName, InputStream inputStream,String path);
}
