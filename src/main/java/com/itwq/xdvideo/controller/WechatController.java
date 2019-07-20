package com.itwq.xdvideo.controller;


import com.itwq.xdvideo.config.WeChatConfig;
import com.itwq.xdvideo.domain.JsonData;
import com.itwq.xdvideo.domain.User;
import com.itwq.xdvideo.domain.VideoOrder;
import com.itwq.xdvideo.service.UserService;
import com.itwq.xdvideo.service.VideoOrderService;
import com.itwq.xdvideo.utils.JwtUtils;
import com.itwq.xdvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private VideoOrderService videoOrderService;
    /**
     * 拼装扫一扫二维码
     * @return
     */
            @GetMapping("login_url")
            @ResponseBody
           public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {

                    String redirectUrl=weChatConfig.getOpenRedirectUrl();//获取开放平台重定向地址
                    String callbackUrl= URLEncoder.encode(redirectUrl,"GBK");//进行编码
                    String qrcodeUrl=String.format(weChatConfig.getOPEN_QRCODE_URL(),weChatConfig.getOpenAppId(),callbackUrl,accessPage);
                    return JsonData.buildSuccess(qrcodeUrl);
           }

    /**
     * 微信扫码登录回调地址
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
           @GetMapping("/user/callback")
           public void wechatUserCallback(@RequestParam(value = "code",required = true) String code,
                                          String state, HttpServletResponse response) throws IOException {


                  User user = userService.saveWeChatUser(code);
                  if (user!=null){
                      //生成jwt
                     String token= JwtUtils.geneJsonWebToken(user);
                     response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+
                             "&name="+URLEncoder.encode(user.getName(),"UTF-8"));
                  }

           }
    /**
     * 微信支付回调
     */
    @RequestMapping("/order/callback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream = request.getInputStream();
        //是包装设计模式，性能更高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line=in.readLine())!=null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap= WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap.toString());

        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);
        //判断签名是否正确
        if (WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getKey())){
            if ("SUCCESS".equals(sortedMap.get("result_code"))){

                String outTradeNo=sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);
                if (dbVideoOrder.getState()==0){//判断逻辑看业务场景
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setState(1);
                    int rows=videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    if (rows==1){//通知微信订单处理成功
                             response.setContentType("text/html");
                             response.getWriter().println("success");
                    }
                }


            }
        }
        //处理失败
        response.setContentType("text/html");
        response.getWriter().println("fail");
    }
}
