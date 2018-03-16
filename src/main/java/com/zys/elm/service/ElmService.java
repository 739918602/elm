package com.zys.elm.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.zys.elm.models.ElmCookie;
import com.zys.elm.models.HongBaoBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/3/15
 * @Time: 18:39
 */
@Slf4j
@Service
public class ElmService {
    @Autowired
    private MongoTemplate template;
    static String url = "https://h5.ele.me/restapi/marketing/promotion/weixin/E4FE53B6EF2B888BAC2F6E0B8FB08417";
    public static HongBaoBean getHongbao(String group_sn, String sign,String phone){
        Connection conn = Jsoup.connect(url)
                .ignoreContentType(true)
                .header("Content-Type","application/json")
                .requestBody("{\"method\":\"phone\",\"group_sn\":\""+group_sn+"\",\"sign\":\""+sign+"\",\"phone\":\""+phone+"\",\"device_id\":\"\",\"hardware_id\":\"\",\"platform\":0,\"track_id\":\"undefined\",\"weixin_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/E5C59809F107C96E15816B55BCC81009/40\",\"weixin_username\":\"-1\",\"unionid\":\"fuck\"}");

        String json = null;
        try {
            json = conn.post().text();
        } catch (IOException e) {
            log.info(json);
            log.error("请求失败:{}",e);
        }
        if(StringUtils.isBlank(json)){

            log.info("接口请求失败!");
            return null;
        }
        log.info(json);
        HongBaoBean hongBaoBean = JSONObject.parseObject(json, HongBaoBean.class);
        return hongBaoBean;
    }
    public boolean addCookie(String cookie){
        String eleme_key = StringUtils.substringBetween(cookie,"eleme_key\":\"","\",");
        HongBaoBean hongBaoBean = getHongbao("29eb176201b0980c",eleme_key,"");
        if(hongBaoBean!=null){
            ElmCookie elmCookie = new ElmCookie();
            elmCookie.setElemeKey(eleme_key);
            elmCookie.setPhone(hongBaoBean.getAccount());
            Query query = new Query();
            query.addCriteria(Criteria.where("phone").is(elmCookie.getPhone()));
            Update update = new Update();
            update.addToSet("elemeKey",elmCookie.getElemeKey());
            UpdateResult result = template.upsert(query, update,ElmCookie.class);
            return result.getMatchedCount()>0;
        }
        return false;
    }

    public boolean getMaxHongBao(String phone,String hbUrl){
        String sn = StringUtils.substringBetween(hbUrl,"&sn=","&");
        String lucky_number = StringUtils.substringBetween(hbUrl,"&lucky_number=","&");
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").nin(phone));
        List<ElmCookie> cookies = template.find(query,ElmCookie.class);
        if(cookies==null || cookies.size()==0){
            return false;
        }
        try {
            cookies.forEach(cookie->{
                HongBaoBean hongBaoBean = getHongbao(sn,cookie.getElemeKey(),cookie.getPhone());
                int currentSize = hongBaoBean.getPromotion_records().size() + 2;
                if(currentSize == Integer.valueOf(lucky_number)){
                    Query q = new Query();
                    q.addCriteria(Criteria.where("phone").is(phone));
                    ElmCookie ck = template.findOne(q,ElmCookie.class);
                    HongBaoBean maxHongBao = getHongbao(sn,ck.getElemeKey(),ck.getPhone());
                    log.info(maxHongBao.toString());
                    return;
                }
            });
        }catch (Exception e){
            log.error("领取失败,{}",e);
            return false;
        }
        return true;
    }
}