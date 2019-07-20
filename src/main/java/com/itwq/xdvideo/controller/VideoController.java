package com.itwq.xdvideo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itwq.xdvideo.domain.Video;
import com.itwq.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 分页接口
     * @param page    当前第几页,默认是第一页
     * @param size    每页显示几条
     * @return
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                            @RequestParam(value= "size",defaultValue = "5")int size){

        PageHelper.startPage(page,size);

        List<Video> list = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<Video>(list);


        Map<String, Object> data = new HashMap<>();
        data.put("total_size",pageInfo.getTotal());//总条数
        data.put("total_pages",pageInfo.getPages());//总页数
        data.put("current_page",page);//当前页
        data.put("data",pageInfo.getList());//数据
        return data;

    }

    /**
     * 根据id找视频
     * @param videoId
     * @return
     */
    @GetMapping("find_byid")
    public Object findById(@RequestParam(value = "video_id",required = true)int videoId){
        return videoService.findById(videoId);
    }


}
