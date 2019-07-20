package com.itwq.xdvideo.service.impl;

import com.itwq.xdvideo.config.WeChatConfig;
import com.itwq.xdvideo.dao.UserMapper;
import com.itwq.xdvideo.domain.User;
import com.itwq.xdvideo.service.UserService;
import com.itwq.xdvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public User saveWeChatUser(String code) {

        String accessTokenUrl = String.format(weChatConfig.getOPEN_ACCESS_TOKEN_URL(),weChatConfig.getOpenAppId(),weChatConfig.getOpenAppSecret(),code);
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);
         if (baseMap==null||baseMap.isEmpty())return null;
        String access_token = (String) baseMap.get("access_token");
        String openid=(String) baseMap.get("openid");

        User dbUser = userMapper.findByopenid(openid);
        if (dbUser!=null){//更新用户,直接返回
                return dbUser;
        }

        //获取用户信息
        String userInfoUrl=String.format(weChatConfig.getOPEN_USER_INFO_URL(),access_token,openid);
        Map<String, Object> baseUserMap = HttpUtils.doGet(userInfoUrl);
        if (baseUserMap==null||baseUserMap.isEmpty())return null;
        String nickname = (String) baseUserMap .get("nickname");
        Double sexTmep=(Double) baseUserMap .get("sex");
        int sex=sexTmep.intValue();

        String province = (String) baseUserMap .get("province");
        String city = (String) baseUserMap .get("city");
        String country = (String) baseUserMap .get("country");
        String headimgurl = (String) baseUserMap .get("headimgurl");
        StringBuilder sb=new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress=sb.toString();
        try {
            //解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setCity(finalAddress);
        user.setOpenid(openid);
        user.setSex(sex);
        user.setCreateTime(new Date());

        userMapper.save(user);
         return user;

    }
}
