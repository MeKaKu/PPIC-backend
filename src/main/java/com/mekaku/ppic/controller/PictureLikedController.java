package com.mekaku.ppic.controller;

import com.mekaku.ppic.service.PictureLikedService;
import com.mekaku.ppic.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PictureLikedController {

    @Autowired
    PictureLikedService pictureLikedService;

    @Autowired
    PictureService pictureService;

    //判断指定用户是否为指定图片点赞了
    @GetMapping("/PictureLiked/IsUserLikedPicture")
    boolean isUserLikedPicture(int uid,int pid){
        return pictureLikedService.isUserLikedPicture(uid,pid);
    }

    //更新点赞情况
    @PutMapping("/PictureLiked/UpdatePictureLiked")
    void updatePictureLiked(int uid,int pid,boolean isLiked){
        pictureLikedService.updatePictureLiked(uid,pid,isLiked);

        //更新picture数据表的like_count值
        int like_count = pictureLikedService.getPictureLikedCountByPid(pid);
        pictureService.updatePictureLikedCount(pid,like_count);
    }

    //获取指定用户喜欢的图片数量
    @GetMapping("/PictureLiked/GetPictureLikedCount")
    int getPictureLikedCount(int uid){
        return pictureLikedService.getPictureLikedCount(uid);
    }

}
