package com.mekaku.ppic.controller;

import com.mekaku.ppic.entity.Comment;
import com.mekaku.ppic.entity.CommentTree;
import com.mekaku.ppic.service.CommentLikedService;
import com.mekaku.ppic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentLikedService commentLikedService;

    //获取主评论列表
    @GetMapping("/Comment/GetMainCommentList")
    List<CommentTree> getMainCommentList(int pid){
        return commentService.getMainCommentList(pid);
    }

    //获取子评论列表
    @GetMapping("/Comment/GetSubCommentList")
    List<Comment> getSubCommentList(int cid){
        return commentService.getSubCommentList(cid);
    }

    //获取评论列表
    @GetMapping("/Comment/GetCommentList")
    List<CommentTree> getCommentList(int uid,int pid){
        List<CommentTree> comments = commentService.getCommentList(pid);
        for(CommentTree comment : comments){
            //判断用户是否有点赞
            comment.setLiked(commentLikedService.isUserLikedComment(uid,comment.getCid()));
            //comment.setLike_count(commentLikedService.getCommentLikedCountByCid(comment.getCid()));
        }
        return comments;
    }

    //创建评论
    @PostMapping("/Comment/CreateComment")
    CommentTree createComment(String from_username,int pid,String content,int uid){
        return commentService.createComment(from_username,pid,content,uid);
    }

    //创建回复
    @PostMapping("/Comment/CreateReply")
    Comment createReply(@RequestBody Comment comment){
        return commentService.createReply(comment);
    }
}
