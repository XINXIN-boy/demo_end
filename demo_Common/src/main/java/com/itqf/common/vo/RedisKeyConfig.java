package com.itqf.common.vo;

import lombok.Data;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/519:48
 * description:
 */
@Data
public class RedisKeyConfig {

    //订单分布式锁的
    public static final String ORDER_LOCK = "order:lock:" ;  //加skuid
    public static int LOCK_TIME=3;

    //存储订单信息 String类型  -有效期2小时
    public static String ORDER_V2="order:v2:";//订单id  值：订单对象
    //存储订单详情-Hash类型 key field:skuid  value:订单详情对象
    public static String ORDERITEM_V2="order:item:";//订单id
    //订单数据 有效期2小时
    public static int ORDER_TIME=2;


}
