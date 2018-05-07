package com.zys.elm.service;

import com.alibaba.fastjson.JSONObject;
import com.zys.elm.models.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/5/7
 * @Time: 15:18
 */
@Slf4j
@Service
public class VideoService {
    @Autowired
    private MongoTemplate template;

    public List<Video> findVideoByName(String name,String sort){
        Query query = new Query();
        query.limit(10);
        name = ".*?"  + name + ".*";
        log.info("入参:{}",name);
        query.addCriteria(Criteria.where("name").regex(name));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,sort)));
        List<Video> result =  template.find(query,Video.class);
        return result;
    }
}