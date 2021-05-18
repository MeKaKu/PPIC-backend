package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.Album;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AlbumMapper {

    //新增相册
    @Insert("insert into album (uid,name,date,tip,url) values(#{uid},#{name},#{date},#{tip},#{url})")
    int addAlbum(Album album);

    //删除指定aid的相册
    @Delete("delete from album where aid=#{aid}")
    int deleteAlbum(int aid);

    //修改相册信息（name，tip）
    @Update("update album set name=#{name},tip=#{tip} where aid=#{aid}")
    int updateAlbum(int aid,String name,String tip);

    //修改相册首页图片地址
    @Update("update album set url=#{url} where aid=#{aid}")
    int updateAlbumUrl(int aid,String url);

    //分页查找指定用户的相册
    @Select("select * from album where uid=#{uid} order by date desc limit #{startIndex},#{number}")
    List<Album> queryAlbumByUid(int uid,int startIndex,int number);

    //查询指定用户的所有相册
    @Select("select * from album where uid=#{uid} order by date desc")
    List<Album> queryAllAlbumByUid(int uid);

    //查找指定相册，by（uid，name，date）
    @Select("select * from album where uid=#{uid} and name=#{name} and date=#{date}")
    Album queryAlbumByDate(int uid,String name,String date);

    //根据aid查询指定的相册
    @Select("select * from album where aid=#{aid}")
    Album queryAlbumByAid(int aid);

    @Select("select * from album")
    List<Album> queryAllAlbumList();

    //查询指定用户的相册数量
    @Select("select count(*) from album where uid=#{uid}")
    int queryAlbumCountByUid(int uid);

    //相册名模糊查询
    @Select("select * from album where name like #{keyword} limit #{startIndex},#{number}")
    List<Album> queryAlbumByKeyword(String keyword,int startIndex,int number);
}
