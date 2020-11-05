package com.itqf.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:50
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id ;
    private Integer aid ;
    private Integer uid ;
    private Double totalmoney ;
    private Double paymoney ;
    private Double freemoney ;
    private Integer flag ;
    private Date ctime ;
    private Date uitme ;

    public Order(String id, Integer aid, Integer uid , int flag) {
        this.id = id;
        this.aid = aid;
        this.uid = uid;
        this.flag = flag ;
    }
}
