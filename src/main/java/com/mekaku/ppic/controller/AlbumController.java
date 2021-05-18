package com.mekaku.ppic.controller;

import com.mekaku.ppic.entity.Album;
import com.mekaku.ppic.entity.Picture;
import com.mekaku.ppic.service.AlbumService;
import com.mekaku.ppic.service.CollectionService;
import com.mekaku.ppic.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    PictureService pictureService;

    //创建一个新的相册
    @PostMapping("/Album/CreateAlbum")
    Album createAlbum(@RequestBody Album album){
        return albumService.createAlbum(album);
    }

    //删除一个相册
    @PostMapping("/Album/DeleteAlbum")
    int deleteAlbum(int aid){
        collectionService.deleteCollectionByAid(aid);
        List<Picture> lp = pictureService.getAllPictureByAid(aid);
        for(Picture p:lp){
            int collect_count = collectionService.getPictureCollectedCount(p.getPid());
            pictureService.updatePictureCollectCount(p.getPid(),collect_count);
        }
        return albumService.deleteAlbum(aid);
    }

    //修改相册信息
    @PostMapping("/Album/UpdateAlbum")
    int updateAlbum(int aid,String name,String tip){
        return albumService.updateAlbum(aid,name,tip);
    }

    //获取指定用户的相册列表
    @GetMapping("/Album/GetAlbumByUid")
    List<Album> getAlbumByUid(int uid,int startIndex,int number){
        List<Album> albumList = albumService.getAlbumByUid(uid,startIndex,number);
        for(Album album : albumList){
            album.setPicture_count(collectionService.getPictureCountByAid(album.getAid()));
        }
        return albumList;
    }

    //获取指定用户的相册数目
    @GetMapping("/Album/GetAlbumCountByUid")
    int getAlbumCountByUid(int uid){
        return albumService.getAlbumCountByUid(uid);
    }

    //根据aid获取指定的相册
    @GetMapping("/Album/GetAlbumByAid")
    Album getAlbumByAid(int aid){
        Album album = albumService.getAlbumByAid(aid);
        album.setPicture_count(collectionService.getPictureCountByAid(album.getAid()));
        return album;
    }

    //获取指定用户的所有相册
    @GetMapping("/Album/GetAllAlbumByUid")
    List<Album> getAllAlbumByUid(int uid,int pid){
        List<Album> albumList = albumService.getAllAlbumByUid(uid);
        for(Album album : albumList){
            //相册内是否收藏了指定图片
            album.setCollected(collectionService.getIsCollectedByAid(album.getAid(),pid));
        }
        return albumList;
    }

    //根据相册名模糊匹配
    @GetMapping("/Album/GetAlbumByKeyword")
    List<Album> getAlbumByKeyword(String keyword,int startIndex,int number){
        List<Album> albumList = albumService.getAlbumByKeyword(keyword,startIndex,number);
        for(Album album : albumList){
            album.setPicture_count(collectionService.getPictureCountByAid(album.getAid()));
        }
        return albumList;
    }

}
