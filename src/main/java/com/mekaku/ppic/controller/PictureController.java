package com.mekaku.ppic.controller;

import com.mekaku.ppic.entity.Picture;
import com.mekaku.ppic.mapper.PictureMapper;
import com.mekaku.ppic.service.PictureService;
import com.mekaku.ppic.util.UpLoad;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PictureController {

    @Autowired
    PictureService pictureService;

    //上传图片
    @PostMapping("/Picture/UpLoadPicture")
    String upLoadPicture(@Param("fileName") MultipartFile file, String tag, int uid){
        return pictureService.upLoadPicture(file,tag,uid);
        //return pictureService.testSave(file,"smgcz","jpg");
        //return false;
    }

    //上传图片Test
    @PostMapping("/Picture/UpLoadPictureTest")
    String upLoadPictureTest(@Param("fileName") MultipartFile file, String tag, int uid){
        System.out.println("backEndFileName: "+file.getName()+" "+tag+" "+uid);
//        System.out.println("frontEndFileName: "+file.getOriginalFilename());
//        System.out.println("tag = "+tag);
//        System.out.println("uid = "+uid);
//        System.out.println(UpLoad.upLoadFile(file,"test02","jpg"));
        //System.out.println(file.getOriginalFilename()+" "+sex+" "+tip);
        return pictureService.testSave(file,"smgcz","jpg");
    }

    //获取number张图片，startIndex是开始编号（前面已经获取了多少张图片），orderBy是排序方式
    @GetMapping("/Picture/PictureStream")
    List<Picture> getPictureStream(int startIndex,String orderBy,int number){
        return pictureService.getPictureStream(startIndex,orderBy,number);
    }

    //根据pid获取指定的图片信息
    @GetMapping("/Picture/PictureDetail")
    Picture getPictureDetail(int pid,boolean addViewFlag){
        return pictureService.getPictureByPid(pid,addViewFlag);
    }

    //获取指定用户喜欢的图片
    @GetMapping("/Picture/GetPictureLikedByUser")
    List<Picture> getPictureLikedByUser(int uid,int startIndex,int number){
        return pictureService.getPictureLikedByUser(uid,startIndex,number);
    }

    //获取指定相册内的图片
    @GetMapping("/Picture/GetPictureListByAid")
    List<Picture> getPictureListByAid(int aid,int startIndex,int number){
        return pictureService.getPictureListByAid(aid,startIndex,number);
    }

    //分页获取指定用户上传的图片
    @GetMapping("/Picture/GetPictureListByUid")
    List<Picture> getPictureListByUid(int uid,int startIndex,int number){
        return pictureService.getPictureListByUid(uid,startIndex,number);
    }

    //获取用户上传的图片数目
    @GetMapping("/Picture/GetPictureCountByUid")
    int getPictureCountByUid(int uid){
        return pictureService.getPictureCountByUid(uid);
    }

    //根据图片标签获取图片
    @GetMapping("/Picture/GetPictureByKeyword")
    List<Picture> getPictureByKeyword(String keyword,int startIndex,int number){
        return pictureService.getPictureByKeyword(keyword,startIndex,number);
    }

}
