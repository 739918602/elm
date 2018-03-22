package com.zys.elm.ctr;

import com.zys.elm.service.ElmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/3/15
 * @Time: 18:39
 */
@Slf4j
@Controller
public class ElmCtr {
    @Autowired
    private ElmService service;
    @RequestMapping(value = "/addCookie",method = RequestMethod.POST)
    public String addCookie(Model mod,String cookie){
        return service.addCookie(mod,cookie);
    }
    @RequestMapping(value = "/getMaxHongBao",method = RequestMethod.POST)
    public String getMaxHongBao(Model mod,String phone,String hbUrl){
        log.info("{},红包领取,{}",phone,hbUrl);
        return service.getMaxHongBao(mod,phone,hbUrl);
    }
    @RequestMapping("/")
    public String welcome(){
        return "index";
    }
}