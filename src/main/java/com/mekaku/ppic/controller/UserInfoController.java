package com.mekaku.ppic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mekaku.ppic.entity.UserInfo;

import com.mekaku.ppic.service.UserInfoService;

import com.mekaku.ppic.util.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtUtil jwtUtil;

    //用户登录
    @PostMapping("/User/Login")
    void userLogin(String name, String pwd,HttpServletResponse response){
        //return userInfoService.userLogin(name,pwd);// 登录判定
        UserInfo user = userInfoService.userLogin(name, pwd);
        // 生成token
        String token = jwtUtil.createJWT(user);
        user.setToken(token);
        //Map<String,Object> map = new HashMap<>();
        //map.put("user",user);
        //map.put("token",token);
        //return Response.ok().data(token);
        //response.setContentType("text/html");
        //response.setIntHeader("mmmms",2);
        //response.setHeader("token","token");
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(user);
            response.getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //response.setHeader("token",token);
        //System.out.println(response.getHeader("token"));
        //return response;
    }

    //用户注册
    @PostMapping("User/Register")
    void register(String name,String pwd,char sex,String phone,String tip){
        userInfoService.createUser(name,pwd,sex,phone,tip);
    }

    //验证用户名是否存在
    @GetMapping("/User/CheckUser")
    boolean checkUser(String name){
        return userInfoService.checkUser(name);
    }

    //验证手机号是否已经被注册
    @PostMapping("/User/CheckPhone")
    boolean checkPhone(String phone){
        return userInfoService.checkPhone(phone);
    }

    //根据用户名，获取用户信息
    @GetMapping("/User/GetUserInfoByName")
    UserInfo getUserInfoByName(String name){
        return userInfoService.getUserInfoByName(name);
    }

    //根据用户uid获取指定用户的信息
    @GetMapping("/User/GetUserByUid")
    UserInfo getUserByUid(int uid){
        return userInfoService.getUserByUid(uid);
    }

    //修改个人信息（头像，性别，简介）
    @PostMapping("/User/UploadUserIcon")
    void uploadUserIcon(@Param("fileName") MultipartFile file, char sex, String tip,int uid){
        System.out.println(file.getOriginalFilename()+" "+uid+" "+sex+" "+tip);
        if(userInfoService.changeUserIcon(uid,file)){
            userInfoService.updateUserInfo(uid,sex,tip);
        }
    }

    //修改个人信息（性别，简介）
    @PostMapping("/User/UpdateUserInfo")
    UserInfo updateUserInfo(char sex, String tip,int uid){
        return userInfoService.updateUserInfo(uid,sex,tip);
    }

    //根据用户名模糊匹配
    @GetMapping("/User/GetUserByKeyword")
    List<UserInfo> getUserByKeyword(String keyword,int startIndex,int number){
        return userInfoService.getUserByKeyword(keyword,startIndex,number);
    }

    //获取注册验证码
    @GetMapping("/User/GetRegisterCheckCode")
    String getRegisterCheckCode(String phone){
        return userInfoService.getRegisterCheckCode(phone);
    }

    //获取修改密码的验证码
    @GetMapping("/User/GetChangePwdCheckCode")
    String getChangePwdCheckCode(String phone){
        return userInfoService.getChangePwdCheckCode(phone);
    }

    //根据手机号修改密码
    @PostMapping("/User/ChangePwdByPhone")
    int changePwdByPhone(String phone){
        return userInfoService.changePwdByPhone(phone);
    }
}
