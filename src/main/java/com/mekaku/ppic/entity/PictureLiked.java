package com.mekaku.ppic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureLiked {
    private int uid;
    private int pid;
    private String date;
}
