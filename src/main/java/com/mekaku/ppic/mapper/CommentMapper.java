package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.Comment;
import com.mekaku.ppic.entity.CommentTree;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    //添加主评论（针对于图片的评论）
    @Insert("insert into comment (pid,from_username,content,date,uid) values (#{pid},#{from_username},#{content},#{date},#{uid})")
    int addMainComment(Comment c);

    //添加子评论（添加回复）
    @Insert("insert into comment (pid,from_username,content,to_cid,belong_cid,to_username,date,uid) values (#{pid},#{from_username},#{content},#{to_cid},#{belong_cid},#{to_username},#{date},#{uid})")
    int addSubComment(Comment c);

    //删除指定cid的评论
    @Delete("delete from comment where cid=#{cid}")
    int deleteCommentByCid(int cid);

    //更新点赞数
    @Update("update comment set like_count=#{like_count} where cid=#{cid}")
    int updateCommentLikeCount(int cid,int like_count);

    //更新回复数
    @Update("update comment set reply_count=#{reply_count} where cid=#{cid}")
    int updateCommentReplyCount(int cid,int reply_count);

    //查询所有评论
    @Select("select * from comment")
    List<Comment> queryCommentList();

    //查询指定图片下面的所有评论（根据pid查询评论）
    @Select("select * from comment where pid=#{pid}")
    List<Comment> queryCommentByPid(int pid);

    //查询指定用户的所有评论（根据用户名查询评论）
    @Select("select * from comment where from_username=#{from_username}")
    List<Comment> queryCommentByUid(String from_username);

    //查询主评论（针对于图片的评论）
    @Select("select * from comment where pid=#{pid} and to_cid=0")
    List<CommentTree> queryMainCommentByPid(int pid);

    //查询子评论（主评论下的子评论）
    @Select("select * from comment where belong_cid=#{belong_cid}")
    List<Comment> querySubCommentByCid(int belong_cid);

    //查询指定评论的所有回复
    @Select("select * from comment where to_cid=#{to_cid}")
    List<Comment> queryReplyByCid(int to_cid);

    //查询指定主评论
    @Select("select * from comment where date=#{date} and uid=#{uid} and pid=#{pid}")
    CommentTree queryMainCommentByDate(Comment c);

    //查询指定子评论
    @Select("select * from comment where date=#{date} and uid=#{uid} and pid=#{pid}")
    Comment querySubCommentByDate(Comment c);
}
