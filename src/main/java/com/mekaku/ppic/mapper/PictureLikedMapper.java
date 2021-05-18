package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.PictureLiked;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureLikedMapper {

    //查询全部内容
    @Select("select * from pictureLiked")
    List<PictureLiked> queryAllPictureLiked();

    //查询指定用户喜欢的图片，从startIndex开始的number张图片
    @Select("select * from pictureLiked where uid=#{uid} limit #{startIndex},#{number}")
    List<PictureLiked> queryPictureLikedByUid(int uid,int startIndex,int number);

    //查询指定用户喜欢的图片数
    @Select("select count(*) from pictureLiked where uid=#{uid}")
    int queryPictureLikedCountByUid(int uid);

    //查询指定图片被点赞次数
    @Select("select count(*) from pictureLiked where pid=#{pid}")
    int queryPictureLikedCountByPid(int pid);

    //查询指定用户是否为指定图片点赞
    @Select("select count(*) from pictureLiked where uid=#{uid} and pid=#{pid}")
    int queryIsUserLikedPicture(int uid,int pid);

    //新增点赞记录
    @Insert("insert into pictureLiked (uid,pid,date) values (#{uid},#{pid},#{date})")
    int addPictureLiked(int uid,int pid,String date);

    //删除点赞记录
    @Delete("delete from pictureLiked where uid=#{uid} and pid=#{pid}")
    int deletePictureLiked(int uid,int pid);

    //查询指定用户喜欢的图片总数
    @Select("select count(*) from pictureLiked where uid=#{uid}")
    int queryPictureLikedCount(int uid);
}
