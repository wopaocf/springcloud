package com.itwq.xdvideo.service;

import com.itwq.xdvideo.dao.VideoMapper;
import com.itwq.xdvideo.domain.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceTest {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void findAll() {
        List<Video> list = videoService.findAll();
        assertNotNull(list);
        for (Video video:list){
            System.out.println(video.getTitle());
        }

    }

    @Test
    public void findById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}