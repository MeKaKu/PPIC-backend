package com.mekaku.ppic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    private int uid;
    private int aid;
    private String name;
    private String date;
    private String tip;
    private String url;
    private int picture_count;
    private boolean collected;
}
