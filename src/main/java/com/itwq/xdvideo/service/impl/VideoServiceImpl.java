package com.itwq.xdvideo.service.impl;

import com.itwq.xdvideo.dao.VideoMapper;
import com.itwq.xdvideo.domain.Video;
import com.itwq.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(Integer id) {
        return videoMapper.findById(id);
    }

    @Override
    public int update(Video video) {
       return videoMapper.update(video);
    }

    @Override
    public int delete(Integer id) {
       return videoMapper.delete(id);
    }
}
