package com.mekaku.ppic.service;

import com.mekaku.ppic.entity.Picture;
import com.mekaku.ppic.mapper.PictureLikedMapper;
import com.mekaku.ppic.mapper.PictureMapper;
import com.mekaku.ppic.util.UpLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PictureService {

    @Autowired
    PictureMapper pictureMapper;

    //瀑布流获取number张图片，orderBy决定排序类型{view：浏览量，like：点赞数，collect：收藏数，date：时间降（新），dateAsc：时间升（旧）}默认view
    public List<Picture> getPictureStream(int startIndex,String orderBy,int number){
        List<Picture> lp;
        switch (orderBy) {
            case "like":
                lp = pictureMapper.queryPictureOrderByLike(startIndex,number);
                break;
            case "collect":
                lp = pictureMapper.queryPictureOrderByCollect(startIndex,number);
                break;
            case "date":
                lp = pictureMapper.queryPictureOrderByDateDesc(startIndex,number);
                break;
            case "dateAsc":
                lp = pictureMapper.queryPictureOrderByDateAsc(startIndex,number);
                break;
            default:
                lp = pictureMapper.queryPictureOrderByView(startIndex,number);
        }
        int constWidth = 200;
        for(Picture p : lp){
            p.setHeight(p.getHeight()*constWidth/p.getWidth());
            p.setWidth(constWidth);
        }
        return lp;
    }

    //上传图片
    public String upLoadPicture(MultipartFile file,String tag,int uid){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String name = file.getOriginalFilename();
        //System.out.println("backEndFileName:"+file.getName());
        //System.out.println("frontEndFileName:"+name);
        if(name == null) return "false";
        Picture p = new Picture();
        int sum = pictureMapper.queryPictureSum() + 1;
        String [] subs =name.split("[.]");
        String suffix = subs.length>1?subs[subs.length - 1]:"jpg";
        String prefix = "ppic" + sum;
        String url = "/"+prefix+"."+suffix;
        p.setUrl(url);
        p.setTag(tag);
        p.setUid(uid);
        p.setDate(date);
        //获取上传图片的宽高
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bufferedImage !=null){
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            p.setWidth(width);
            p.setHeight(height);
        }
        //System.out.println(p);
        if(pictureMapper.addPicture(p)>0){
            return testSave(file,prefix,suffix);
        }
        return "error";
    }

    //testSave
    public String testSave(MultipartFile file,String prefix,String suffix){
        String path = "/home/ppic/pic/"+prefix;
        System.out.println("path="+path);
        File localFile = new File(path+"/"+prefix+"."+suffix);
        localFile.setWritable(true,false);
        System.out.println(path+"/"+prefix+"."+suffix);
        if(!localFile.getParentFile().exists()){
            localFile.getParentFile().mkdirs();
        }
        if(!localFile.exists()){
            try {
                localFile.setReadable(true,false);
                localFile.setWritable(true,false);
                localFile.setExecutable(false,false);
                System.out.println("create?="+localFile.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(file.getOriginalFilename() + file.getSize());
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(UpLoad.upLoadLocalFile(localFile,prefix,suffix,"picture"));
        return path;
    }

    //根据pid获取指定图片
    public Picture getPictureByPid(int pid,boolean addViewFlag){
        Picture p = pictureMapper.queryPictureByPid(pid);
        if(addViewFlag){
            //更新浏览量（？
            p.setView_count(p.getView_count() + 1);
            pictureMapper.updateViewCount(pid,p.getView_count());
        }
        //更新点赞数
        //p.setLike_count(pictureLikedMapper.queryPictureLikedCountByPid(pid));
        //pictureMapper.updateLikeCount(pid,p.getLike_count());
        //
        return p;
    }

    //更新指定图片的点赞数
    public void updatePictureLikedCount(int pid,int like_count){
        pictureMapper.updateLikeCount(pid,like_count);
    }

    //更新指定图片的收藏数
    public void updatePictureCollectCount(int pid,int collect_count){
        pictureMapper.updateCollectCount(pid,collect_count);
    }

    //获取指定用户喜欢的图片
    public List<Picture> getPictureLikedByUser(int uid,int startIndex,int number){
        return pictureMapper.queryPictureLikedByUser(uid,startIndex,number);
    };

    //获取相册内的图片
    public List<Picture> getPictureListByAid(int aid,int startIndex,int number){
        return pictureMapper.queryPictureListByAid(aid,startIndex,number);
    }

    //获取指定相册下面的所有图片
    public List<Picture> getAllPictureByAid(int aid) {
        return pictureMapper.queryAllPictureByAid(aid);
    }

    //分页获取指定用户上传的图片
    public List<Picture> getPictureListByUid(int uid, int startIndex, int number) {
        return pictureMapper.queryPictureByUid(uid,startIndex,number);
    }

    //获取指定用户上传的图片数
    public int getPictureCountByUid(int uid) {
        return pictureMapper.queryPictureCountByUid(uid);
    }

    //根据图片标签模糊匹配
    public List<Picture> getPictureByKeyword(String keyword,int startIndex,int number){
        return pictureMapper.queryPictureByKeyword("%"+keyword+"%",startIndex,number);
    }

}
