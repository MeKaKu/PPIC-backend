package com.mekaku.ppic.service;

import com.mekaku.ppic.entity.Album;
import com.mekaku.ppic.mapper.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    AlbumMapper albumMapper;

    //创建相册
    public Album createAlbum(Album album){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        album.setDate(date);
        album.setUrl("/album.jpg");
        if(albumMapper.addAlbum(album)>0){
            return albumMapper.queryAlbumByDate(album.getUid(),album.getName(),date);
        }
        return new Album();
    }

    //删除相册
    public int deleteAlbum(int aid){
        return albumMapper.deleteAlbum(aid);
    }

    //修改相册信息
    public int updateAlbum(int aid,String name,String tip){
        return albumMapper.updateAlbum(aid,name,tip);
    }

    //修改相册首页图片地址
    public int updateAlbumUrl(int aid,String url){
        return albumMapper.updateAlbumUrl(aid,url);
    }

    //获取指定用户的相册列表
    public List<Album> getAlbumByUid(int uid,int startIndex,int number){
        return albumMapper.queryAlbumByUid(uid,startIndex,number);
    }

    //获取指定用户的相册数目
    public int getAlbumCountByUid(int uid){
        return albumMapper.queryAlbumCountByUid(uid);
    }

    //根据aid获取指定的相册
    public Album getAlbumByAid(int aid){
        return albumMapper.queryAlbumByAid(aid);
    }

    //获取指定用户的所有相册
    public List<Album> getAllAlbumByUid(int uid){
        return albumMapper.queryAllAlbumByUid(uid);
    }

    //根据相册名模糊匹配
    public List<Album> getAlbumByKeyword(String keyword,int startIndex,int number){
        return albumMapper.queryAlbumByKeyword("%"+keyword+"%",startIndex,number);
    }

}
