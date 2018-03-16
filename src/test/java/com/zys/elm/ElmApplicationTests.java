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

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ElmApplicationTests {
	String url = "https://h5.ele.me/restapi/marketing/promotion/weixin/E5C59809F107C96E15816B55BCC81009";
	private final static String qqUserAgent = "User agent string ( Mozilla/5.0 (Linux; Android 5.1; m1 metal Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043409 Safari/537.36 V1ANDSQ7.2.5744YYBD QQ/7.2.5.3305 NetType/WIFI WebP/0.3.0 Pixel/1080 )";
	final static String cookie = "ubt_ssid=h7dnwntdzbqnkwjv1jnsmxhwquclkfv8_2017-09-26; perf_ssid=y54fw3f2qos5bm8p6tzdl79d0vqbligm_2017-09-26; _utrace=22fae2c9581ee9e7e9341c28bc1a44d7_2017-09-26; snsInfo[101204453]={\"city\":\"仙桃\",\"eleme_key\":\"62d1f6c376f6450bdc8962b758966722\",\"figureurl\":\"http://qzapp.qlogo.cn/qzapp/101204453/E5C59809F107C96E15816B55BCC81009/30\",\"figureurl_1\":\"http://qzapp.qlogo.cn/qzapp/101204453/E5C59809F107C96E15816B55BCC81009/50\",\"figureurl_2\":\"http://qzapp.qlogo.cn/qzapp/101204453/E5C59809F107C96E15816B55BCC81009/100\",\"figureurl_qq_1\":\"http://thirdqq.qlogo.cn/qqapp/101204453/E5C59809F107C96E15816B55BCC81009/40\",\"figureurl_qq_2\":\"http://thirdqq.qlogo.cn/qqapp/101204453/E5C59809F107C96E15816B55BCC81009/100\",\"gender\":\"男\",\"is_lost\":0,\"is_yellow_vip\":\"0\",\"is_yellow_year_vip\":\"0\",\"level\":\"0\",\"msg\":\"\",\"nickname\":\"-1\",\"openid\":\"E5C59809F107C96E15816B55BCC81009\",\"province\":\"湖北\",\"ret\":0,\"vip\":\"0\",\"year\":\"1996\",\"yellow_vip_level\":\"0\",\"name\":\"-1\",\"avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/E5C59809F107C96E15816B55BCC81009/40\"}";
	@Autowired
	public MongoTemplate template;
	@Test
	public void contextLoads() throws IOException {
		//template.insert("");
		getHongbao("29ea3e90f71c6c2f","62d1f6c376f6450bdc8962b758966722");
	}
	private void getHongbao(String group_sn,String sign){
		Connection conn = Jsoup.connect(url)
				.ignoreContentType(true)
				//.userAgent(qqUserAgent)
				//.header("Cookie",cookie)
				.header("Content-Type","application/json")
				.requestBody("{\"group_sn\":\""+group_sn+"\",\"sign\":\""+sign+"\"}");

		String json = null;
		try {
			json = conn.post().text();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(json)){
			log.info("接口请求失败!");
			return;
		}
		log.info(json);
		HongBaoBean hongBaoBean = JSONObject.parseObject(json, HongBaoBean.class);
		template.insert(hongBaoBean);

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
