package com.zys.elm.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/3/16
 * @Time: 11:12
 */
@Data
@Document(collection = "cookies")
public class ElmCookie {
    private String phone;
    private String elemeKey;
}