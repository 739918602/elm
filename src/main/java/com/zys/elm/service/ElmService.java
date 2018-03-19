package com.zys.elm.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import com.zys.elm.models.ElmCookie;
import com.zys.elm.models.HongBaoBean;
import com.zys.elm.models.Response;
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
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
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
    static String url = "https://h5.ele.me/restapi/marketing/promotion/weixin/";
    public static HongBaoBean getHongbao(String uuid,String group_sn, String sign,String phone){
        Connection conn = Jsoup.connect(url+uuid)
                .ignoreHttpErrors(true)
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
    public String addCookie(Model mod, String cookie){
        try {
            cookie=URLDecoder.decode(cookie,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String eleme_key = StringUtils.substringBetween(cookie,"eleme_key\":\"","\",");
        String uuid = StringUtils.substringBetween(cookie,"openid\":\"","\",");
        System.out.println(eleme_key);
        HongBaoBean hongBaoBean = getHongbao(uuid,"29eb176201b0980c",eleme_key,"");
        if(hongBaoBean!=null&&hongBaoBean.getAccount()!=null){
            log.info("获取红包信息成功,{}",hongBaoBean.getAccount());
            ElmCookie elmCookie = new ElmCookie();
            elmCookie.setElemeKey(eleme_key);
            elmCookie.setUuid(uuid);
            elmCookie.setPhone(hongBaoBean.getAccount());
            elmCookie.setAvailable(true);
            log.info(elmCookie.toString());
            Query query = new Query();
            query.addCriteria(Criteria.where("phone").is(elmCookie.getPhone()));
            Update update = new Update();
            update.addToSet("elemeKey",elmCookie.getElemeKey());
            UpdateResult result = template.upsert(query, update,ElmCookie.class);
            if(result.getMatchedCount()>0){
                log.info("添加成功！");
                mod.addAttribute("addRes",new Response<>(true));
                return "index";
            }
        }else{
            log.info("红包信息获取失败");
        }
        mod.addAttribute("addRes",new Response<>("01","添加失败"));
        return "index";
    }

    public String getMaxHongBao(Model mod ,String phone,String hbUrl){
        String sn ="";
        int lucky_number=0;
        try {
            StringUtils.substringBetween(hbUrl,"&sn=","&");
            lucky_number = Integer.valueOf(StringUtils.substringBetween(hbUrl,"&lucky_number=","&"));
        }catch (Exception e){
            mod.addAttribute("res",new Response<>("11","红包地址错误"));
            log.info("红包地址错误");
            log.error("红包地址错误:{}",e);
            return "index";
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").nin(phone));
        query.addCriteria(Criteria.where("available").nin(true));
        List<ElmCookie> cookies = template.find(query,ElmCookie.class);
        if(cookies==null || cookies.size()==0){
            mod.addAttribute("res",new Response<>("12","没有可用cookies"));
            return "index";
        }
        Iterator<ElmCookie> iterator =  cookies.iterator();
        try {
            int i = 0;
            while ((i < Integer.valueOf(lucky_number))&&iterator.hasNext()) {
                ElmCookie cookie = iterator.next();
                HongBaoBean hongBaoBean = getHongbao(cookie.getUuid(),sn,cookie.getElemeKey(),cookie.getPhone());
                if(hongBaoBean!=null){
                    i++;
                }else{
                    log.info("现有cookie领取红包失败:{}",phone);
                    template.updateFirst(query,Update.update("available",false),ElmCookie.class);
                    continue;
                }
                int currentSize = hongBaoBean.getPromotion_records().size() + 2;
                if(currentSize == Integer.valueOf(lucky_number)){
                    Query q = new Query();
                    q.addCriteria(Criteria.where("phone").is(phone));
                    ElmCookie ck = template.findOne(q,ElmCookie.class);
                    HongBaoBean maxHongBao = getHongbao(ck.getUuid(),sn,ck.getElemeKey(),ck.getPhone());
                    log.info(maxHongBao.toString());
                    break;
                }
            }
            if(i<lucky_number){
                mod.addAttribute("res",new Response<>("13","cookies不够用了"));
                return "index";
            }
       
        }catch (Exception e){
            log.error("领取失败,{}",e);
            mod.addAttribute("res",new Response<>("15","领取失败"));
            return "index";
        }
        mod.addAttribute("res",new Response<>(true));
        return "index";
    }
}