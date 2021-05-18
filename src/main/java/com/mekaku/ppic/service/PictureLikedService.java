package com.mekaku.ppic.service;

import com.mekaku.ppic.mapper.PictureLikedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PictureLikedService {

    @Autowired
    PictureLikedMapper pictureLikedMapper;

    //判断指定用户是否为指定图片点赞了
    public boolean isUserLikedPicture(int uid,int pid){
        return pictureLikedMapper.queryIsUserLikedPicture(uid,pid)>0;
    }

    //获取指定图片的点赞数
    public int getPictureLikedCountByPid(int pid){
        return pictureLikedMapper.queryPictureLikedCountByPid(pid);
    }

    //更新点赞情况
    public void updatePictureLiked(int uid,int pid,boolean isLiked){
        pictureLikedMapper.deletePictureLiked(uid,pid);
        if(isLiked){
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            pictureLikedMapper.addPictureLiked(uid,pid,date);
        }
    }

    //获取指定用户喜欢的图片的数量
    public int getPictureLikedCount(int uid){
        return pictureLikedMapper.queryPictureLikedCount(uid);
    }

}
