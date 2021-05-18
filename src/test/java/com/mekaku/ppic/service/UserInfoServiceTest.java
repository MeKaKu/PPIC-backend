package com.mekaku.ppic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoServiceTest {

    @Autowired
    UserInfoService userInfoService;

    @Test
    public void getRegisterCheckCode(){
        String code = userInfoService.getRegisterCheckCode("13278882303");
        System.out.println(code);
    }

}