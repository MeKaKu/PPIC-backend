package com.mekaku.ppic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    private int uid;
    private String from_username;
    private String content;
    private String date;
    private int is_read;
    private String path;
    private int pid;
    private int cid;
}
