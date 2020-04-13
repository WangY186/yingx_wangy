package com.baizhi.wy;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;

import java.io.File;

public class TestAliyun {

    @Test
    public void createBucket(){

    }
    @Test
    public void upload(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FbmbKS7Ho6ifrvSfap6";
        String accessKeySecret = "IwqYgYseTXRC4Ey96IiucksUt1gL9K";

        String bucket="yingx-wang";
        String fileName="红叶谷.jpg";
        String localFile="D:\\资料\\景点图片\\红叶谷.jpg";

         // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFile));

          // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
         // ObjectMetadata metadata = new ObjectMetadata();
         // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
         // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
