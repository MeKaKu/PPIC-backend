package com.mekaku.ppic.service;

import com.mekaku.ppic.mapper.CommentLikedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikedService {

    @Autowired
    CommentLikedMapper commentLikedMapper;

    //判断用户是否为指定评论点赞
    public boolean isUserLikedComment(int uid,int cid){
        return commentLikedMapper.queryIsUserLikedComment(uid,cid)>0;
    }

    //更新点赞情况
    public void updateCommentLiked(int uid,int cid,boolean isLiked){
        commentLikedMapper.deleteCommentLiked(uid,cid);
        if(isLiked){
            commentLikedMapper.addCommentLiked(uid,cid);
        }
    }

    //获取指定评论的点赞数
    public int getCommentLikedCountByCid(int cid){
        return commentLikedMapper.queryCommentLikedCountByCid(cid);
    }
}
