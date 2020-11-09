package com.itqf.shopingcar.config;

import io.swagger.models.auth.In;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/917:10
 * description:
 */
public class RabbitMQTypeConfig {
    public static final int MQ_CART_ADD = 1;  //添加购物车
    public static final int MQ_CART_UPDATE = 2 ; //修改购物车商品数量
}
