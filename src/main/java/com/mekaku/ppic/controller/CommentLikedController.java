package com.mekaku.ppic.controller;

import com.mekaku.ppic.service.CommentLikedService;
import com.mekaku.ppic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentLikedController {

    @Autowired
    CommentLikedService commentLikedService;

    @Autowired
    CommentService commentService;

    //判断用户是否为指定评论点赞
    @GetMapping("/CommentLiked/IsUserLikedComment")
    boolean isUserLikedComment(int uid,int cid){
        return commentLikedService.isUserLikedComment(uid,cid);
    }

    //更新点赞情况
    @PutMapping("/CommentLiked/UpdateCommentLiked")
    void updateCommentLiked(int uid,int cid,boolean isLiked){
        commentLikedService.updateCommentLiked(uid,cid,isLiked);

        //更新comment数据表的like_count值
        int like_count = commentLikedService.getCommentLikedCountByCid(cid);
        commentService.updateCommentLikeCount(cid,like_count);
    }

}
