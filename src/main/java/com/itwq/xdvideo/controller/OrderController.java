package com.itwq.xdvideo.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itwq.xdvideo.dto.VideoOrderDTO;
import com.itwq.xdvideo.service.VideoOrderService;
import com.itwq.xdvideo.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/api/v1/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;
    @GetMapping("create")
    public void saveOrder(@RequestParam(value = "video_id",required =true)Integer videoId,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        String ip= IpUtils.getIpAddr(request);
        int userId=(Integer) request.getAttribute("user_id");
//        int userId=2;
//        String ip="192.168.0.109";
        VideoOrderDTO videoOrderDTO = new VideoOrderDTO();
        videoOrderDTO.setUserId(userId);
        videoOrderDTO.setVideoId(videoId);
        videoOrderDTO.setIp(ip);

        String codeUrl = videoOrderService.save(videoOrderDTO);
        if (codeUrl==null){
            throw new NullPointerException();
        }
        try{
            //生成二维码配置
            Map<EncodeHintType,Object> hints=new HashMap<>();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            //编码类型
            BitMatrix bitMatrix=new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400,hints);
            OutputStream out = response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
