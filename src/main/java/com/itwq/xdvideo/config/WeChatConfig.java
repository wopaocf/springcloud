package com.itwq.xdvideo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {
    /**
     * 公众号appid
     */
    private  String mpAppId;
    /**
     * 公众号密钥
     */
    private String mpAppSecret;
    /**
     * 开发平台appid
     */
    private String openAppId;
    /**
     * 开发平台密钥
     */
    private String openAppSecret;
    /**
     * 回调地址
     */
    private String openRedirectUrl;
    /**
     * 微信开放平台二维码连接
     */
    private  String OPEN_QRCODE_URL;
    /**
     * 开发平台获取access_token地址
     */
    private  String  OPEN_ACCESS_TOKEN_URL;

    /**
     * 用户信息
     */
   private String OPEN_USER_INFO_URL;
    /**
     * 商户id
     */
    private String mer_id;
    /**
     * 商户密钥
     */
    private String key;

    /**
     * 支付回调地址
     */
    private  String  callback;
    /**
     * 统一下单接口
     */
    private String UNIFIED_ORDER_URL;

}
