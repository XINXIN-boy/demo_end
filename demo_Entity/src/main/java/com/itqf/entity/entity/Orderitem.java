package com.itqf.entity.entity;

import lombok.Data;

import java.util.Date;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:50Order
 * description:
 */
@Data
public class Orderitem {

    private Integer id ;
    private Integer oid ;
    private Integer skuid ;
    private Integer scount ;
    private Double price ;
    private Date ctime ;

    public Orderitem(Integer oid, Integer skuid, Integer scount, Double price, Date ctime) {
        this.oid = oid;
        this.skuid = skuid;
        this.scount = scount;
        this.price = price;
        this.ctime = ctime;
    }
}
