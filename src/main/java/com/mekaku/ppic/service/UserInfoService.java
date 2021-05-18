package com.mekaku.ppic.service;

import com.mekaku.ppic.entity.UserInfo;
import com.mekaku.ppic.mapper.UserInfoMapper;
import com.mekaku.ppic.util.SMS;
import com.mekaku.ppic.util.UpLoad;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    //用户登录
    public UserInfo userLogin(String name,String pwd){
        UserInfo userInfo = userInfoMapper.queryUserInfoByName(name);
        String corPwd = userInfo.getPwd();
        if(corPwd.equals(pwd)){
            userInfo.setPwd("TRUE");
        }
        else{
            userInfo.setPwd("FALSE");
        }
        return userInfo;
    }

    //验证用户名是否存在（是否已经被注册了）
    public boolean checkUser(String name){
        return userInfoMapper.queryUserByName(name)>0;
    }

    //验证手机号是否已经被注册
    public boolean checkPhone(String phone){
        return userInfoMapper.queryUserByPhone(phone)>0;
    }

    //创建一个新的用户
    public void createUser(String name,String pwd,char sex,String phone,String tip){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setPwd(pwd);
        userInfo.setSex(sex);
        userInfo.setPhone(phone);
        userInfo.setTip(tip);
        userInfo.setDate(date);
        userInfo.setUrl("/user-undefined.jpg");
        userInfoMapper.addUserInfo(userInfo);
        UserInfo newUser = userInfoMapper.queryUserInfoByName(name);
        String url = "/user-"+newUser.getUid()+".jpg";
        userInfoMapper.updateUserUrl(newUser.getUid(),url);

        if(sex=='男'){
            UpLoad.copyFile("user/user-1.jpg","user/user-"+newUser.getUid()+".jpg");
        }
        else{
            UpLoad.copyFile("user/user-2.jpg","user/user-"+newUser.getUid()+".jpg");
        }
    }

    //根据用户名，获取用户信息
    public UserInfo getUserInfoByName(String name){
        UserInfo userInfo = userInfoMapper.queryUserInfoByName(name);
        userInfo.setPwd("");
        return userInfo;
    }

    //根据用户uid,，获取用户信息
    public UserInfo getUserByUid(int uid){
        UserInfo userInfo = userInfoMapper.queryUserByUid(uid);
        userInfo.setPwd("");
        return userInfo;
    }

    //修改指定用户的信息
    public UserInfo updateUserInfo(int uid,char sex,String tip){
        userInfoMapper.updateUserInfo(uid,sex,tip);
        return getUserByUid(uid);
    }

    //修改用户头像
    public boolean changeUserIcon(int uid, MultipartFile file){
        String prefix = "user-"+uid;
        String name = file.getOriginalFilename();
        if(name == null) return false;
        String [] subs =name.split("[.]");
        String suffix = subs.length>1?subs[subs.length - 1]:"jpg";
        System.out.println(UpLoad.upLoadFile(file,prefix,suffix,"user"));
        return true;
    }

    //根据用户名模糊匹配
    public List<UserInfo> getUserByKeyword(String keyword,int startIndex,int number){
        return userInfoMapper.queryUserByKeyword("%"+keyword+"%",startIndex,number);
    }

    //获取注册验证码
    public String getRegisterCheckCode(String phone) {
        String code = getCheckCode();
        SMS.sendSms("register",phone,code);
        return code;
    }

    //获取修改密码的验证码
    public String getChangePwdCheckCode(String phone){
        String code = getCheckCode();
        SMS.sendSms("changePassword",phone,code);
        return code;
    }

    //获取验证码
    private String getCheckCode(){
        String code = "";
        Random r = new Random(new Date().getTime());
        for(int i=0;i<6;i++){
            Random rr = new Random(new Date().getTime() + r.nextInt(1000121));
            code = code.concat(""+rr.nextInt(10));
        }
        //System.out.println(code);
        return code;
    }

    //根据手机号修改密码
    public int changePwdByPhone(String phone){
        return userInfoMapper.updatePwdByPhone(phone);
    }
}
