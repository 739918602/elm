package com.zys.elm.ctr;

import com.zys.elm.service.ElmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/3/15
 * @Time: 18:39
 */
@Controller
public class ElmCtr {
    @Autowired
    private ElmService service;
    @RequestMapping(value = "/addCookie",method = RequestMethod.POST)
    public boolean addCookie(String cookie){
        return service.addCookie(cookie);
    }
    @RequestMapping(value = "/getMaxHongBao",method = RequestMethod.POST)
    public boolean getMaxHongBao(String phone,String hbUrl){
        return service.getMaxHongBao(phone,hbUrl);
    }
}