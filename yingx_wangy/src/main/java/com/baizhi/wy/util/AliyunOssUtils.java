package com.baizhi.wy.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
public class AliyunOssUtils {

    // Endpoint以北京为例
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "LTAI4FbmbKS7Ho6ifrvSfap6";
    private static String accessKeySecret = "IwqYgYseTXRC4Ey96IiucksUt1gL9K";
    //存储空间名称
    private static String bucket="yingx-wang";

    //创建存储空间
    public static void createBucket(String bucketName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);

        // 创建存储空间。
        ossClient.createBucket(createBucketRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件上传到本地
    public static void uploadLocal(String fileName,String localFilePath){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFilePath));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传文件到阿里云
    public static void uploadAliyun(MultipartFile headImg,String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。
        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));
        ossClient.putObject(new PutObjectRequest(bucket,  fileName, new ByteArrayInputStream(bytes)).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener()));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //下载文件到本地
    public static void downloadLocal(String fileName,String localFilePath){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucket, fileName), new File(localFilePath));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除文件
    public static void deleteFile(String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucket, fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //视频截帧上传封面图并上传到阿里云
    public static void interceptVideoPhoto(String fileName,String coverName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_2000,f_jpg,w_0,h_0";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        // 上传网络流。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucket, coverName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //主函数测试
    public static void main(String[] args) {
        uploadLocal("衡山.jpg","D:\\资料\\景点图片\\衡山.jpg");

    }


}
