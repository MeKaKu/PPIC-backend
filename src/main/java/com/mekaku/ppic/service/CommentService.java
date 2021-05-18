package com.mekaku.ppic.service;

import com.mekaku.ppic.entity.Comment;
import com.mekaku.ppic.entity.CommentTree;
import com.mekaku.ppic.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    //获取主评论列表
    public List<CommentTree> getMainCommentList(int pid){
        return commentMapper.queryMainCommentByPid(pid);
    }

    //获取回复(子评论)列表
    public List<Comment> getSubCommentList(int cid){
        return commentMapper.querySubCommentByCid(cid);
    }

    //获取评论列表
    public List<CommentTree> getCommentList(int pid){
        List<CommentTree> mainComments= commentMapper.queryMainCommentByPid(pid);
        for(CommentTree comment : mainComments){
            //comment.setLiked(commentLikedService.isUserLikedComment(uid,comment.getCid()));
            //子评论列表
            comment.setReplyList(commentMapper.querySubCommentByCid(comment.getCid()));
            //comment.setLike_count(commentLikedService.getCommentLikedCountByCid(comment.getCid()));
        }
        return mainComments;
    }

    //添加评论
    public CommentTree createComment(String from_username,int pid,String content,int uid){
        Comment comment = new Comment();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        comment.setFrom_username(from_username);
        comment.setPid(pid);
        comment.setContent(content);
        comment.setDate(date);
        comment.setUid(uid);
        commentMapper.addMainComment(comment);
        return commentMapper.queryMainCommentByDate(comment);
    }

    //添加回复
    public Comment createReply(Comment comment){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        comment.setDate(date);
        commentMapper.addSubComment(comment);
        return commentMapper.querySubCommentByDate(comment);
    }

    //更新指定评论的点赞数
    public void updateCommentLikeCount(int cid,int like_count){
        commentMapper.updateCommentLikeCount(cid,like_count);
    }

}
