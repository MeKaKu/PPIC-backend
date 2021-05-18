package com.mekaku.ppic.controller;

import com.mekaku.ppic.entity.Picture;
import com.mekaku.ppic.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    PictureMapper pictureMapper;

    @GetMapping("/testSQL/queryPictureByTag/{tag}")
    List<Picture> queryPictureByTag(@PathVariable("tag") String tag){
        String newTag = "%"+tag+"%";
        return pictureMapper.queryPictureByTag(newTag);
    }
}
