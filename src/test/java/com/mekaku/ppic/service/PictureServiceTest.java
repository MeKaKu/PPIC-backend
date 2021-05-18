package com.mekaku.ppic.service;

import com.mekaku.ppic.entity.Picture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PictureServiceTest {

    @Autowired
    PictureService pictureService;

    @Test
    public void getPictureStream01(){
        System.out.println("start");
        List<Picture> lp = pictureService.getPictureStream(1,"view",20);
        System.out.println(lp);
        System.out.println("end");
    }

    @Test
    public void getPictureLikedByUser(){
        System.out.println("start");
        List<Picture> lp = pictureService.getPictureLikedByUser(1,0,20);
        System.out.println(lp);
        System.out.println("end");
    }
}