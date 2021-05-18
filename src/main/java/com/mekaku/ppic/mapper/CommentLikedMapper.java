package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.CommentLiked;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface CommentLikedMapper {

    //新增评论点赞
    @Insert("insert into commentLiked (uid,cid) values (#{uid},#{cid})")
    int addCommentLiked(int uid,int cid);

    //删除评论点赞
    @Delete("delete from commentLiked where uid=#{uid} and cid=#{cid}")
    int deleteCommentLiked(int uid,int cid);

    //查询指定用户是否为指定评论点赞
    @Select("select count(*) from commentLiked where uid=#{uid} and cid=#{cid}")
    int queryIsUserLikedComment(int uid,int cid);

    //查询指定评论的点赞数
    @Select("select count(*) from commentLiked where cid=#{cid}")
    int queryCommentLikedCountByCid(int cid);

    //查询全部内容
    @Select("select * from commentLiked")
    List<CommentLiked> queryCommentLikedList();

}
