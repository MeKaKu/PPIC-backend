package com.mekaku.ppic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int uid;
    private String name;
    private String pwd;
    private char sex;
    private String phone;
    private String tip;
    private String date;
    private String url;
    private int forum_count;
    private String token;
}
