package com.itwq.xdvideo.dao;

import com.itwq.xdvideo.domain.Video;
import com.itwq.xdvideo.provider.VideoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface VideoMapper {


    @Select("select * from video")
    List<Video> findAll();

    @Select("select * from video where id=#{id}")
    Video findById(Integer id);

   // @Update("update video set title=#{title} where id=#{id}")
    @UpdateProvider(type = VideoProvider.class,method = "updateVideo")
    int update(Video video);

    @Delete("delete from video where id=#{id}")
    int delete(Integer id);

    @Insert("inser into video(title,summary,cover_img,view_num,price,create_time,online,point)"+
            "values"+
             "(#{title},#{summary},#{coverImg},#{viewNum},#{price},#{createTime},#{online},#{point})")
    //获得自增主键
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int save(Video video);


}
