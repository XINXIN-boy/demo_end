package com.itqf.entity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:50Order
 * description:
 */
@Data
public class Orderitem  implements Serializable {

    private Integer id ;
    private String oid ;
    private Integer skuid ;
    private Integer scount ;
    private Double price ;
    private Date ctime ;

    public Orderitem(String oid, Integer skuid, Integer scount, Double price) {
        this.oid = oid;
        this.skuid = skuid;
        this.scount = scount;
        this.price = price;
    }
}
