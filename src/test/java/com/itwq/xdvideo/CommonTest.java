package com.itwq.xdvideo;

import com.itwq.xdvideo.domain.User;
import com.itwq.xdvideo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;

public class CommonTest {

    @Test
    public void test(){
        User user = new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("xd");

        String token = JwtUtils.geneJsonWebToken(user);
        System.out.print(token);
    }

    @Test
    public void testcheck(){
         String token="eyJhbGciOiJIUzI1NiJ9" +
                 ".eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6InhkIiwiaW1nIjoid3d3LnhkY2xhc3MubmV0IiwiaWF0IjoxNTU3MTUxMDQzLCJleHAiOjE1NTc3NTU4NDN9" +
                 ".uX3a2aFePsgZa_fyUJ91IxGKQDaZqavsU-3yrbjvMls";
        Claims claims = JwtUtils.checkJWT(token);
        if (claims!=null){
            String name = (String) claims.get("name");
            Integer id=(Integer) claims.get("id");
            String img = (String)claims.get("img");
            System.out.println(name);
            System.out.println(id);
            System.out.println(img);

        }
    }
}
