package com.itqf.entity.entity;

import lombok.Data;

import java.util.Date;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:50
 * description:
 */
@Data
public class Order {

    private Integer id ;
    private Integer aid ;
    private Integer uid ;
    private Double totalmoney ;
    private Double paymoney ;
    private Double freemoney ;
    private Integer flag ;
    private Date ctime ;
    private Date uitme ;

    public Order(Integer aid, Integer uid, Double totalmoney, Double paymoney, Double freemoney, Integer flag) {
        this.aid = aid;
        this.uid = uid;
        this.totalmoney = totalmoney;
        this.paymoney = paymoney;
        this.freemoney = freemoney;
        this.flag = flag;
    }
}
