package com.mekaku.ppic.controller;

import com.mekaku.ppic.entity.Picture;
import com.mekaku.ppic.service.AlbumService;
import com.mekaku.ppic.service.CollectionService;
import com.mekaku.ppic.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @Autowired
    PictureService pictureService;

    @Autowired
    AlbumService albumService;

    //获取指定相册下面的图片数量
    @GetMapping("/Collection/GetPictureCountByAid")
    int getPictureCountByAid(int aid){
        return collectionService.getPictureCountByAid(aid);
    }

    //更新收藏情况
    @PutMapping("/Collection/UpdateCollection")
    void updateCollection(int pid,int aid,boolean collected){
        collectionService.updateCollection(pid,aid,collected);

        //更新图片的collect_count
        int collect_count = collectionService.getPictureCollectedCount(pid);
        pictureService.updatePictureCollectCount(pid,collect_count);

        //更新相册封面图片
        List<Picture> lp = pictureService.getPictureListByAid(aid,0,1);
        String url = "/album.jpg";
        if(lp.size()>0){
            url = lp.get(0).getUrl();
        }
        albumService.updateAlbumUrl(aid,url);
    }

}
