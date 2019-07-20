package com.itwq.xdvideo.dao;

import com.itwq.xdvideo.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    /**
     * 根据主键Id查找
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
     User findById(@Param("id") int userId);

    /**
     * 根据openid找用户
     * @param openid
     * @return
     */
    @Select("select * from user where  openid=#{openid}")
    User findByopenid(@Param("openid") String openid);

    /**
     * 增加用户信息
     * @param user
     * @return
     */
    @Insert("insert into user(openid,name,head_img,phone,sign,sex,city,create_time) " +
            "values(#{openid},#{name},#{headImg},#{phone},#{sign},#{sex},#{city},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")//可以拿到主键信息
    int save(User user);


}
