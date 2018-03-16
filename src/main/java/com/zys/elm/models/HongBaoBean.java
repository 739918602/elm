package com.zys.elm.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/3/15
 * @Time: 18:22
 */
@NoArgsConstructor
@Data
@Document(collection = "hongbao")
public class HongBaoBean implements Serializable{

    /**
     * account : 13023175728
     * lucky_status : 2
     * promotion_items : [{"amount":1,"expire_date":"2018-03-15","hongbao_variety":["全品类"],"item_type":1,"name":"拼手气红包","phone":"13023175728","source":"weixin_share_hongbao","sum_condition":35,"validity_periods":"限2018-03-15当天使用"}]
     * promotion_records : [{"amount":1,"created_at":1521100120,"is_lucky":false,"sns_avatar":"","sns_username":"130****5728"}]
     * ret_code : 2
     * theme_id : 1833
     */

    private String account;
    private int lucky_status;
    private int ret_code;
    private int theme_id;
    private List<PromotionItemsBean> promotion_items;
    private List<PromotionRecordsBean> promotion_records;

    @NoArgsConstructor
    @Data
    public static class PromotionItemsBean {
        /**
         * amount : 1
         * expire_date : 2018-03-15
         * hongbao_variety : ["全品类"]
         * item_type : 1
         * name : 拼手气红包
         * phone : 13023175728
         * source : weixin_share_hongbao
         * sum_condition : 35
         * validity_periods : 限2018-03-15当天使用
         */

        private int amount;
        private String expire_date;
        private int item_type;
        private String name;
        private String phone;
        private String source;
        private int sum_condition;
        private String validity_periods;
        private List<String> hongbao_variety;
    }

    @NoArgsConstructor
    @Data
    public static class PromotionRecordsBean {
        /**
         * amount : 1
         * created_at : 1521100120
         * is_lucky : false
         * sns_avatar :
         * sns_username : 130****5728
         */

        private int amount;
        private int created_at;
        private boolean is_lucky;
        private String sns_avatar;
        private String sns_username;
    }
}