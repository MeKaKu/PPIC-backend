package com.mekaku.ppic.controller;

import com.mekaku.ppic.entity.Msg;
import com.mekaku.ppic.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MsgController {

    @Autowired
    MsgService msgService;

    //获取未读消息总数
    @GetMapping("/Msg/GetMsgCount")
    int getMsgCount(int uid,String name){
        return msgService.getMsgCount(uid,name);
    }

    //获取未读评论信息数
    @GetMapping("/Msg/GetCommentMsgCount")
    int getCommentMsgCount(int uid){
        return msgService.getCommentMsgCount(uid);
    }

    //获取未读回复消息数
    @GetMapping("/Msg/GetReplyMsgCount")
    int getReplyMsgCount(String name){
        return msgService.getReplyMsgCount(name);
    }

    //获取未读点赞消息数
    @GetMapping("/Msg/GetLikedMsgCount")
    int getLikedMsgCount(int uid){
        return msgService.getPictureLikedCount(uid);
    }

    //获取评论消息数
    @GetMapping("/Msg/GetAllCommentMsgCount")
    int getAllCommentMsgCount(int uid){
        return msgService.getAllCommentMsgCount(uid);
    }

    //获取回复消息数
    @GetMapping("/Msg/GetAllReplyMsgCount")
    int getAllReplyMsgCount(String name){
        return msgService.getAllReplyMsgCount(name);
    }

    //获取点赞消息数
    @GetMapping("/Msg/GetAllLikeMsgCount")
    int getAllLikeMsgCount(int uid){
        return msgService.getAllLikeCount(uid);
    }

    //获取消息列表
    @GetMapping("/Msg/GetMsgList")
    List<Msg> getMsgList(int uid,String name,int startIndex,int number,int type){
        return msgService.getMsgList(uid,name,startIndex,number,type);
    }

    //更新评论或者回复消息已读状态
    @PutMapping("/Msg/UpdateCommentOrReplyMsg")
    int updateCommentOrReplyMsg(int cid){
        return msgService.updateCommentOrReplyMsg(cid);
    }

    //更新图片点赞消息已读状态
    @PutMapping("/Msg/UpdateLikeMsg")
    int updateLikeMsg(int uid,int pid){
        return msgService.updateLikeMsg(uid,pid);
    }

    //更新消息状态
    @PutMapping("/Msg/UpdateMsg")
    void updateMsg(int uid,String name,int type){
        msgService.updateMsg(uid,name,type);
    }

}
