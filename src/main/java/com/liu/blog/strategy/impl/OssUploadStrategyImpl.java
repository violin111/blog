package com.liu.blog.strategy.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.liu.blog.config.OssConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * oss的上传策略
 */
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends  AbstractUploadStrategyImpl{
    @Autowired
    private OssConfigProperties ossConfigProperties;
    @Override
    public Boolean exists(String filePath) {
        return getOssClient().doesObjectExist(ossConfigProperties.getBucketName(),filePath);
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws IOException {
        //path + fileName是将路径和文件名组合，然后和配置文件中url拼接一个新的url。inputStream这个是文件中的内容
        getOssClient().putObject(ossConfigProperties.getBucketName(),path+fileName,inputStream);
    }

    @Override
    public String  getFileAccessUrl(String filePath) {
        return ossConfigProperties.getUrl()+filePath;
    }

    /**
     * 获取ossClient
     * @return
     */
    private OSS getOssClient(){
        return new OSSClientBuilder().build(ossConfigProperties.getEndpoint(),
                ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
    }
}
