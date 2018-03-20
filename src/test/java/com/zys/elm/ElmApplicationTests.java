package com.zys.elm;

import com.alibaba.fastjson.JSONObject;
import com.zys.elm.models.HongBaoBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
@Slf4j
public class ElmApplicationTests {
	String url = "https://h5.ele.me/restapi/marketing/promotion/weixin/E5C59809F107C96E15816B55BCC81009";
	private final static String qqUserAgent = "__guid=154668833.684733284776586600.1521509837953.8892; ubt_ssid=nxrautvsk793iwnkaf022edgrblxd14v_2018-03-20; perf_ssid=uizp1sujssmvtk8s85upl97qbxvo5dbv_2018-03-20; _utrace=354d52e76dc48c7bf5846e015d1e16f0_2018-03-20; snsInfo[101204453]={\"city\":\"怀化\",\"eleme_key\":\"f0208489e8f4b2a89c43fd0b1e45a8cb\",\"figureurl\":\"http://qzapp.qlogo.cn \n" +
			"/qzapp/101204453/9E4F50FEB7F0E87B97C8782B819A90BA/30\",\"figureurl_1\":\"http://qzapp.qlogo.cn \n" +
			"/qzapp/101204453/9E4F50FEB7F0E87B97C8782B819A90BA/50\",\"figureurl_2\":\"http://qzapp.qlogo.cn \n" +
			"/qzapp/101204453/9E4F50FEB7F0E87B97C8782B819A90BA/100\",\"figureurl_qq_1\":\"http://thirdqq.qlogo.cn \n" +
			"/qqapp/101204453/9E4F50FEB7F0E87B97C8782B819A90BA/40\",\"figureurl_qq_2\":\"http://thirdqq.qlogo.cn \n" +
			"/qqapp/101204453/9E4F50FEB7F0E87B97C8782B819A90BA/100\",\"gender\":\"男\",\"is_lost\":0,\"is_yellow_vip\":\"0\",\"is_yellow_year_vip\":\"0\",\"level\":\"0\",\"msg\":\"\",\"nickname\":\"–\",\"openid\":\"9E4F50FEB7F0E87B97C8782B819A90BA\",\"province\":\"湖南\",\"ret\":0,\"vip\":\"0\",\"year\":\"2013\",\"yellow_vip_level\":\"0\",\"name\":\"–\",\"avatar\":\"http://thirdqq.qlogo.cn \n" +
			"/qqapp/101204453/9E4F50FEB7F0E87B97C8782B819A90BA/40\"}; track_id=1521510118|77ea7992fa365bf65d5f93138d161e33f0ea5913d50fb60eea|3dbcd33477be5e819c3787422cd40068; monitor_count=4";
	@Autowired
	public MongoTemplate template;
	@Test
	public void contextLoads() throws IOException {
		//template.insert("");
		getHongbao("29ebf7e4391f1c2f","62d1f6c376f6450bdc8962b758966722");
	}
	private void getHongbao(String group_sn,String sign){
		Connection conn = Jsoup.connect(url)
				.method(Connection.Method.POST)
				.ignoreHttpErrors(true)
				.ignoreContentType(true)
				//.userAgent(qqUserAgent)
				//.header("Cookie",cookie)
				.header("Content-Type","application/json")
				.requestBody("{\"sign\":\""+sign+"\"" +
						",\"phone\":\""+"13023175728"+"\"" +
						",\"group_sn\":\""+ group_sn +"\"" +
						"}");
		String json = null;
		try {
			json = conn.execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(json)){
			log.info("接口请求失败!");
			return;
		}
		log.info(json);
		HongBaoBean hongBaoBean = JSONObject.parseObject(json, HongBaoBean.class);
		//template.insert(hongBaoBean);

	}
	@Test
	public void connTest(){
		String u = "https://h5.ele.me/hongbao/#hardware_id=&is_lucky_group=True&lucky_number=8&track_id=&platform=4&sn=29ea3e90f71c6c2f&theme_id=2241&device_id=";
		String sn = StringUtils.substringBetween(u,"&sn=","&");
		String lucky_number = StringUtils.substringBetween(u,"&lucky_number=","&");
		System.out.println(sn);
		System.out.println(lucky_number);
	}
}
