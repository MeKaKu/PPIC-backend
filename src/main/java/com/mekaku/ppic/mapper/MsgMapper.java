package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Mapper
@Repository
public interface MsgMapper {

    //查询评论消息数
    @Select("select count(*) from comment c,picture p where c.pid=p.pid and p.uid=#{uid} and c.to_cid=0")
    int queryAllCommentMsgCount(int uid);

    //查询回复消息数
    @Select("select count(*) from comment where to_username=#{name} and from_username!=#{name} and to_cid!=0")
    int queryAllReplyMsgCount(String name);

    //查询未读图片点赞的消息总数
    @Select("select count(*) from pictureLiked pd,picture p where pd.pid=p.pid and p.uid=#{uid}")
    int queryAllPictureLikedMsgCount(int uid);

    //查询未读评论和回复的消息总数
    @Select("select count(*) from comment where is_read=0 and to_username=#{name} and from_username!=#{name}")
    int queryCommentAndReplyMsgCount(String name);

    //查询未读评论的消息总数
    //@Select("select count(*) from comment where is_read=0 and to_username=#{name} and from_username!=#{name} and to_cid=0")
    @Select("select count(*) from comment c,picture p where c.pid=p.pid and c.is_read=0 and p.uid=#{uid} and c.to_cid=0")
    int queryCommentMsgCount(int uid);

    //查询未读回复的消息总数
    @Select("select count(*) from comment where is_read=0 and to_username=#{name} and from_username!=#{name} and to_cid!=0")
    int queryReplyMsgCount(String name);

    //查询未读图片点赞的消息总数
    @Select("select count(*) from pictureLiked pd,picture p where pd.pid=p.pid and pd.is_read=0 and p.uid=#{uid}")
    int queryPictureLikedMsgCount(int uid);

    //分页查询评论消息
    @Select("select * from comment c,picture p where c.pid=p.pid and p.uid=#{uid} and c.to_cid=0 order by c.date desc limit #{startIndex},#{number}")
    List<Msg> getCommentMsg(int uid,int startIndex,int number);

    //分页查询回复消息
    @Select("select * from comment where to_username=#{name} and from_username!=#{name} and to_cid!=0 order by date desc limit #{startIndex},#{number}")
    List<Msg> getReplyMsg(String name,int startIndex,int number);

    //分页查询图片点赞消息
    @Select("select pd.uid,pd.date as date,u.name as from_username,is_read,pd.pid as pid from pictureLiked pd,picture p,userInfo u where pd.pid=p.pid and p.uid=#{uid} and pd.uid=u.uid order by pd.date limit #{startIndex},#{number}")
    List<Msg> getLikedMsg(int uid,int startIndex,int number);

    //更新评论或者回复消息为已读
    @Update("update comment set is_read=1 where cid=#{cid}")
    int updateCommentOrReplyMsg(int cid);

    //更新图片点赞消息为已读
    @Update("update pictureLiked set is_read=1 where pid=#{pid} and uid=#{uid}")
    int updatePictureLikedMsg(int uid,int pid);

    //查询未读评论的消息
    //@Select("select count(*) from comment where is_read=0 and to_username=#{name} and from_username!=#{name} and to_cid=0")
    @Select("select * from comment c,picture p where c.pid=p.pid and c.is_read=0 and p.uid=#{uid} and c.to_cid=0")
    List<Msg> queryCommentMsg(int uid);

    //查询未读回复的消息
    @Select("select * from comment where is_read=0 and to_username=#{name} and from_username!=#{name} and to_cid!=0")
    List<Msg> queryReplyMsg(String name);

    //查询未读图片点赞的消息
    @Select("select * from pictureLiked pd,picture p where pd.pid=p.pid and pd.is_read=0 and p.uid=#{uid}")
    List<Msg> queryPictureLikedMsg(int uid);

}
