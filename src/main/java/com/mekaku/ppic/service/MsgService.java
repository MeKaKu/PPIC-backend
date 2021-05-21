package com.mekaku.ppic.service;

import com.mekaku.ppic.entity.Msg;
import com.mekaku.ppic.mapper.MsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgService {

    @Autowired
    MsgMapper msgMapper;

    //获取未读消息总数
    public int getMsgCount(int uid,String name){
        int ret = 0;
        //ret += msgMapper.queryCommentAndReplyMsgCount(name);
        ret += msgMapper.queryCommentMsgCount(uid);
        ret += msgMapper.queryReplyMsgCount(name);
        ret += msgMapper.queryPictureLikedMsgCount(uid);
        return ret;
    }

    //获取未读评论消息数
    public int getCommentMsgCount(int uid){
        return msgMapper.queryCommentMsgCount(uid);
    }

    //获取未读回复消息数
    public int getReplyMsgCount(String name){
        return msgMapper.queryReplyMsgCount(name);
    }

    //获取未读图片点赞消息数
    public int getPictureLikedCount(int uid){
        return msgMapper.queryPictureLikedMsgCount(uid);
    }

    //获取评论消息数
    public int getAllCommentMsgCount(int uid){
        return msgMapper.queryAllCommentMsgCount(uid);
    }

    //获取回复消息数
    public int getAllReplyMsgCount(String name){
        return msgMapper.queryAllReplyMsgCount(name);
    }

    //获取点赞消息数
    public int getAllLikeCount(int uid){
        return msgMapper.queryAllPictureLikedMsgCount(uid);
    }

    //获取评论消息
    public List<Msg> getMsgList(int uid,String name,int startIndex,int number,int type){
        if(type==0){
            return msgMapper.getCommentMsg(uid,startIndex,number);
        }
        else if(type==1){
            return msgMapper.getReplyMsg(name,startIndex,number);
        }
        else if(type==2){
            return msgMapper.getLikedMsg(uid,startIndex,number);
        }
        return null;
    }

    //更新评论或者回复消息已读状态
    public int updateCommentOrReplyMsg(int cid){
        return msgMapper.updateCommentOrReplyMsg(cid);
    }

    //更新图片点赞消息已读状态
    public int updateLikeMsg(int uid,int pid){
        return msgMapper.updatePictureLikedMsg(uid,pid);
    }

    //更新信息状态
    public void updateMsg(int uid,String name,int type){
        if(type==0){
            List<Msg> lm = msgMapper.queryCommentMsg(uid);
            for(Msg msg : lm){
                msgMapper.updateCommentOrReplyMsg(msg.getCid());
            }
        }
        else if(type==1){
            List<Msg> lm = msgMapper.queryReplyMsg(name);
            for(Msg msg : lm){
                msgMapper.updateCommentOrReplyMsg(msg.getCid());
            }
        }
        else if(type==2){
            List<Msg> lm = msgMapper.queryPictureLikedMsg(uid);
            for(Msg msg : lm){
                msgMapper.updatePictureLikedMsg(msg.getUid(),msg.getPid());
            }
        }
    }

}
