package com.mekaku.ppic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private int pid;
    private String url;
    private String tag;
    private int uid;
    private String date;
    private int view_count;
    private int like_count;
    private int collect_count;
    private int height;
    private int width;
}
