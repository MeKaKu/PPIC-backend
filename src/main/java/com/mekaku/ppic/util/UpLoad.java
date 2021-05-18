package com.mekaku.ppic.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//腾讯 COS 对象存储服务
public class UpLoad {
    // 1 初始化用户身份信息（secretId, secretKey）。
    private static final String secretId = "AKIDYu66ssLCVeJhpjRflcIH0SikqhPumIEI";
    private static final String secretKey = "zSfcOc3NVYv97pMCud6Lq6B1g5C2CKDo";
    private static final COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    private static final Region region = new Region("ap-chengdu");//成都
    private static final ClientConfig clientConfig = new ClientConfig(region);
    // 这里建议设置使用 https 协议
    //clientConfig.setHttpProtocol(HttpProtocol.https);
    // 3 生成 cos 客户端。
    private static final COSClient cosClient = new COSClient(cred, clientConfig);

    // 指定文件将要存放的存储桶
    private static final String bucketName = "ppic-1301960877";

    public static String upLoadFile(MultipartFile file,String name,String suffix,String dir){
        // 指定要上传的文件
        File localFile = null;
        String key = "";
        try {
            localFile = File.createTempFile(name,suffix);
            file.transferTo(localFile);
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            key = dir + "/" + name + "." + suffix;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            //PutObjectResult putObjectResult =
            cosClient.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/"+key;
    }

    public static String upLoadLocalFile(File localFile,String name,String suffix,String dir){
        //上传本地图片
        String key = "";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        key = dir + "/" + name + "." + suffix;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        //PutObjectResult putObjectResult =
        cosClient.putObject(putObjectRequest);
        return "/"+key;
    }

    public static String copyFile(String srcKey,String destKey){
        // 同地域同账号拷贝
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, srcKey, bucketName, destKey);
        cosClient.copyObject(copyObjectRequest);
        return srcKey + " to " + destKey;
    }
}
