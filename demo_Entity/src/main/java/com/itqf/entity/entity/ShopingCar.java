package com.itqf.entity.entity;

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
public class ShopingCar {

    private Integer id ;
    private Integer skuid ;
    private Integer uid ;
    private Integer scount ;  //计数
    private Integer jprice ;  //价格
    private Date ctime ;

    public ShopingCar(Integer skuid, Integer uid, Integer scount, Integer jprice) {
        this.skuid = skuid;
        this.uid = uid;
        this.scount = scount;
        this.jprice = jprice;
    }
}
