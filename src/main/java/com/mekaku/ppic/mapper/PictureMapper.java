package com.mekaku.ppic.mapper;

import com.mekaku.ppic.entity.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {

    //查询图片总数
    @Select("select count(*) from picture")
    int queryPictureSum();

    //查询所有图片
    @Select("select * from picture")
    List<Picture> queryAllPicture();

    //按浏览量降序排列，查询从startIndex开始的number条数据（下标从0开始）
    @Select("select * from picture order by picture.view_count desc limit #{startIndex},#{number}")
    List<Picture> queryPictureOrderByView(int startIndex,int number);

    //按点赞数降序排列，查询从startIndex开始的number条数据
    @Select("select * from picture order by picture.like_count desc limit #{startIndex},#{number}")
    List<Picture> queryPictureOrderByLike(int startIndex,int number);

    //按收藏数降序排列，查询从startIndex开始的number条数据
    @Select("select * from picture order by picture.collect_count desc limit #{startIndex},#{number}")
    List<Picture> queryPictureOrderByCollect(int startIndex,int number);

    //按上传时间降序排列，查询从startIndex开始的number条数据（最新上传的）
    @Select("select * from picture order by picture.date desc limit #{startIndex},#{number}")
    List<Picture> queryPictureOrderByDateDesc(int startIndex,int number);

    //按上传时间升序排列，查询从startIndex开始的number条数据（最早上传的）
    @Select("select * from picture order by picture.date asc limit #{startIndex},#{number}")
    List<Picture> queryPictureOrderByDateAsc(int startIndex,int number);

    //指定图片pid查询图片
    @Select("select * from picture where picture.pid = #{pid}")
    Picture queryPictureByPid(int pid);

    //查询指定用户uid上传的所有图片
    //@Select("select * from picture where picture.uid = #{uid}")
    //List<Picture> queryPictureByUid(int uid);

    //查询指定用户uid上传的图片从startIndex开始的number张图片
    @Select("select * from picture where picture.uid = #{uid} order by date desc limit #{startIndex},#{number}")
    List<Picture> queryPictureByUid(int uid,int startIndex,int number);

    //添加图片
    @Insert("insert into picture (url,tag,uid,date,height,width) values (#{url},#{tag},#{uid},#{date},#{height},#{width})")
    int addPicture(Picture p);

    //修改指定图片pid的标签tag
    @Update("update picture set tag=#{tag} where pid=#{pid}")
    int updateTag(int pid,String tag);

    //删除图片
    @Delete("delete from picture where pid=#{pid}")
    int deletePicture(int pid);

    //查询与指定标签相同的图片
    @Select("select * from picture where tag like #{tag}")
    List<Picture> queryPictureByTag(String tag);

    //修改浏览量
    @Update("update picture set view_count=#{view_count} where pid=#{pid}")
    int updateViewCount(int pid,int view_count);

    //修改点赞数
    @Update("update picture set like_count=#{like_count} where pid=#{pid}")
    int updateLikeCount(int pid,int like_count);

    //修改收藏数
    @Update("update picture set collect_count=#{collect_count} where pid=#{pid}")
    int updateCollectCount(int pid,int collect_count);

    //查询指定用户喜欢的number张图片，时间降序排列，从startIndex开始的number张图片
    @Select("select * from picture p,pictureLiked pd where p.pid=pd.pid and pd.uid=#{uid} order by pd.date desc limit #{startIndex},#{number}")
    List<Picture> queryPictureLikedByUser(int uid,int startIndex,int number);

    //查询指定相册下的图片
    @Select("select * from picture p,collection c where p.pid=c.pid and c.aid=#{aid} order by c.date desc limit #{startIndex},#{number}")
    List<Picture> queryPictureListByAid(int aid,int startIndex,int number);

    //获取指定相册下面的所有图片
    @Select("select * from picture p,collection c where p.pid=c.pid and c.aid=#{aid} order by c.date desc")
    List<Picture> queryAllPictureByAid(int aid);

    //查询指定用户上传的图片数
    @Select("select count(*) from picture p where uid=#{uid}")
    int queryPictureCountByUid(int uid);

    //根据标签模糊查询
    @Select("select * from picture where tag like #{keyword} limit #{startIndex},#{number}")
    List<Picture> queryPictureByKeyword(String keyword,int startIndex,int number);
}
