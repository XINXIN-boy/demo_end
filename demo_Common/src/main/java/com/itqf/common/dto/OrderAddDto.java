package com.itqf.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:50
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddDto {

//    private int type;//标记位 1.立即购买 2.购物车

    private Integer uid ;
    private Integer aid ;
    private List<GoodsDto> list ;

    private String skuids;//skuid-skuid

}
