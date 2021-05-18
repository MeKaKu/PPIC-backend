package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfoMapper {

    @Select("select * from userInfo")
    List<UserInfo> queryUserList();

    //按用户uid查询指定用户
    @Select("select * from userInfo where uid=#{uid}")
    UserInfo queryUserByUid(int uid);

    //按用户名查询用户信息
    @Select("select * from userInfo where name=#{name}")
    UserInfo queryUserInfoByName(String name);

    //按用户名查询用户是否存在
    @Select("select count(*) from userInfo where name=#{name}")
    int queryUserByName(String name);

    //按手机号查询用户是否存在
    @Select("select count(*) from userInfo where phone=#{phone}")
    int queryUserByPhone(String phone);

    //按用户uid查询密码pwd
    @Select("select pwd from userInfo where uid=#{uid}")
    String queryUserPwdByUid(int uid);

    //修改指定uid用户的密码
    @Update("update userInfo set pwd=#{pwd} where uid=#{uid}")
    int updatePwd(int uid,String pwd);

    //修改指定手机号phone用户的密码
    @Update("update userInfo set pwd=#{pwd} where phone=#{phone}")
    int updatePwdByPhone(String phone);

    //修改指定uid用户的name
    //@Update("update userInfo set name=#{name} where uid=#{uid}")
    //int updateUserInfo(UserInfo u);

    //修改指定用户的信息
    @Update("update userInfo set sex=#{sex},tip=#{tip} where uid=#{uid}")
    int updateUserInfo(int uid,char sex,String tip);

    //修改指定用户的URL
    @Update("update userInfo set url=#{url} where uid=#{uid}")
    int updateUserUrl(int uid,String url);

    //增加用户
    @Insert("insert into userInfo (name,pwd,sex,phone,tip,date,url) values(#{name},#{pwd},#{sex},#{phone},#{tip},#{date},#{url})")
    int addUserInfo(UserInfo u);

    //删除指定用户
    @Delete("delete from userInfo where uid=#{uid}")
    int deleteUserInfo(int uid);

    //查询名字与关键字模糊匹配的用户
    @Select("select * from userInfo where name like #{keyword} limit #{startIndex},#{number}")
    List<UserInfo> queryUserByKeyword(String keyword,int startIndex,int number);

}
