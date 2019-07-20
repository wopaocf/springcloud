package com.itwq.xdvideo.interceptor;


import com.google.gson.Gson;
import com.itwq.xdvideo.domain.JsonData;
import com.itwq.xdvideo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final static Gson gson=new Gson();
    /**
     * 进入controller之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           String token = request.getHeader("token");
        if (token==null){
           token = request.getParameter("token");
        }
        if (token!=null){
            Claims claims = JwtUtils.checkJWT(token);
           if (claims!=null){
               Integer userId=(Integer)claims.get("id");
               String name=(String)claims.get("name");

               request.setAttribute("user_id",userId);
               request.setAttribute("name",name);
               return true;
           }
        }
        sendJsonMessage(response, JsonData.buildError(" 请登录"));
        return true;
    }

    /**
     * 响应数据给前端
     * @param response
     */
    public static void sendJsonMessage(HttpServletResponse response,Object obj) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(obj));
        writer.close();
        response.flushBuffer();
    }

}
