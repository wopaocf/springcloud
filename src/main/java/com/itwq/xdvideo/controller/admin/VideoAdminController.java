package com.itwq.xdvideo.controller.admin;


import com.itwq.xdvideo.domain.Video;
import com.itwq.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;
    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("del_by_id")
    public Object delById(@RequestParam(value = "video_id",required = true)int videoId){
        return videoService.delete(videoId);
    }

    /**
     * 根据id更新视频
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object updateById(@RequestBody Video video){

        return videoService.update(video);
    }
}
