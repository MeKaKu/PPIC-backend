package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionMapper {

    //新建收藏
    @Insert("insert into collection (aid,pid,date) values (#{aid},#{pid},#{date})")
    int addCollection(int aid,int pid,String date);

    //取消收藏
    @Delete("delete from collection where aid=#{aid} and pid=#{pid}")
    int deleteCollection(int aid,int pid);

    //删除指定相册内的所有图片
    @Delete("delete from collection where aid=#{aid}")
    void deleteCollectionByAid(int aid);

    //查询指定相册里面是否收藏了指定图片
    @Select("select count(*) from collection where aid=#{aid} and pid=#{pid}")
    int queryIsCollectedByAid(int aid,int pid);

    //查询指定图片被收藏的次数
    @Select("select count(*) from collection c,album al where c.aid=al.aid and c.pid=#{pid}")
    int queryPictureCollectedCount(int pid);

    //查询指定相册下面的图片数目
    @Select("select count(*) from collection where aid=#{aid}")
    int queryPictureCountByAid(int aid);

    @Select("select * from collection")
    List<Collection> queryCollectionList();

}
