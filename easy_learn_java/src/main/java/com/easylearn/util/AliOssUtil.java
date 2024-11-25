package com.easylearn.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.easylearn.properties.AliOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
public class AliOssUtil {

    @Autowired
    private AliOssProperties aliProperties;
    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        String endpoint = aliProperties.getEndpoint();
        String bucketName = aliProperties.getBucketName();
        String accessKeyId = aliProperties.getAccessKeyId();
        String accessKeySecret = aliProperties.getAccessKeySecret();
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
//        https://web-tlias-yaojiongdong.oss-cn-guangzhou.aliyuncs.com/137de07c-aef2-4582-a775-8543a1db3009.jpg
        //文件访问路径
        String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
        // 关闭ossClient
        ossClient.shutdown();
        log.info("文件路径为,{}",url);
        return url;// 把上传到oss的路径返回
    }

}

