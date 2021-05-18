package com.mekaku.ppic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int cid;
    private int pid;
    private int uid;
    private String date;
    private String content;
    private int like_count;
    private int to_cid;
    private int belong_cid;
    private String to_username;
    private String from_username;
}
