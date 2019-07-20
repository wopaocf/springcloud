package com.itwq.xdvideo.service;

import com.itwq.xdvideo.domain.Video;

import java.util.List;

/**
 * 视频业务类接口
 */
public interface VideoService {

    List<Video> findAll();

    Video findById(Integer id);

    int update(Video video);

    int delete(Integer id);
}
