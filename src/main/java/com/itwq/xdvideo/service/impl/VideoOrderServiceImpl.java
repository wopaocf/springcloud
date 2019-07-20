package com.itwq.xdvideo.service.impl;

import com.itwq.xdvideo.config.WeChatConfig;
import com.itwq.xdvideo.dao.UserMapper;
import com.itwq.xdvideo.dao.VideoMapper;
import com.itwq.xdvideo.dao.VideoOrderMapper;
import com.itwq.xdvideo.domain.User;
import com.itwq.xdvideo.domain.Video;
import com.itwq.xdvideo.domain.VideoOrder;
import com.itwq.xdvideo.dto.VideoOrderDTO;
import com.itwq.xdvideo.service.VideoOrderService;
import com.itwq.xdvideo.utils.CommonUtils;
import com.itwq.xdvideo.utils.HttpUtils;
import com.itwq.xdvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {
    @Autowired
    private VideoMapper  videoMapper;
    @Autowired
    private VideoOrderMapper videoOrderMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private WeChatConfig weChatConfig;
    /**
     *
     * @param videoOrderDTO
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDTO videoOrderDTO) throws Exception {
        /**
         * 查找视频信息
         */
        Video video = videoMapper.findById(videoOrderDTO.getVideoId());
        //查找用户信息
        User user = userMapper.findById(videoOrderDTO.getUserId());
        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());
        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDTO.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());//流水号
        videoOrderMapper.insert(videoOrder);
        //获取codeurl
        String codeUrl = unifiedOrder(videoOrder);

        return codeUrl;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return   videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateVideoOrderByOutTradeNo(VideoOrder videoOrder) {
      return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }

    /**
     * 统一下单方法
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {
        //生成签名
        SortedMap<String,String> params=new TreeMap<>();
        params.put("appid",weChatConfig.getMpAppId());//公众号id
        params.put("mch_id",weChatConfig.getMer_id());//商户id
        params.put("nonce_str",CommonUtils.generateUUID());//随机字符串
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());//订单流水号
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getCallback());
        params.put("trade_type","NATIVE");
        //sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);
        //map转xml
        String patXml = WXPayUtil.mapToXml(params);
        System.out.println(patXml);

        //统一下单
        String orderStr = HttpUtils.dopost(weChatConfig.getUNIFIED_ORDER_URL(), patXml, 4000);
        if (orderStr==null) {
            return null;
        }
        Map<String, String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
       if (unifiedOrderMap!=null){
           return unifiedOrderMap.get("code_url");
       }
        return  null;
    }
}
