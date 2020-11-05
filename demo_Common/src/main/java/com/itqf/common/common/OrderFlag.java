package com.itqf.common.common;


/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time: 2020/11/516:58
 * description: 订单的状态
 */

public enum OrderFlag {

    待支付(1),待发货(2),待签收(3),待确认(4),待评价(5),已评价(6),取消订单(7),超时订单(8) ;

    private int val ;

    private OrderFlag(int v){
        val = v ;
    }

    public int getVal() {
        return val;
    }

}
