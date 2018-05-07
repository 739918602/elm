package com.zys.elm.ctr;

import com.zys.elm.models.Video;
import com.zys.elm.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/5/7
 * @Time: 15:32
 */
@Slf4j
@RestController
public class VideoCtr {
    @Autowired
    private VideoService service;
    @RequestMapping(value = "findVideoByName",method = RequestMethod.GET)
    public List<Video> findVideoByName(String name,String sort){
        return service.findVideoByName(name,sort);
    }
}