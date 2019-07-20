package com.itwq.xdvideo.service;

import com.itwq.xdvideo.domain.VideoOrder;
import com.itwq.xdvideo.dto.VideoOrderDTO;

/**
 * 订单接口
 */
public interface VideoOrderService {
    /**
     * 下单接口
     * @param videoOrderDTO
     * @return
     */
    String save(VideoOrderDTO videoOrderDTO) throws Exception;
    /**
     * 查询流水订单号
     */
    VideoOrder findByOutTradeNo(String outTradeNo);
    /**
     * 更新流水订单号
     */
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}
