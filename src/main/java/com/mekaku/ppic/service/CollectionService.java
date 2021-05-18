package com.mekaku.ppic.service;

import com.mekaku.ppic.mapper.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    //获取指定相册下面的图片数目
    public int getPictureCountByAid(int aid){
        return collectionMapper.queryPictureCountByAid(aid);
    }

    //获取指定图片被用户收藏的次数（按用户人头数）
    public int getPictureCollectedCount(int pid){
        return collectionMapper.queryPictureCollectedCount(pid);
    }

    //更新用户收藏情况
    public void updateCollection(int pid,int aid,boolean collected){
        collectionMapper.deleteCollection(aid,pid);
        if(collected){
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            collectionMapper.addCollection(aid,pid,date);
        }
    }

    //获取指定相册里面是否收藏了指定图片
    public boolean getIsCollectedByAid(int aid,int pid){
        return collectionMapper.queryIsCollectedByAid(aid,pid)>0;
    }

    //删除指定相册内的所有图片
    public void deleteCollectionByAid(int aid) {
        collectionMapper.deleteCollectionByAid(aid);
    }
}
