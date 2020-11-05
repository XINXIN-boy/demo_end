package com.itqf.common.dto;

import lombok.Data;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/512:46
 * description:
 */
@Data
public class OrderUpdateDto {

    private Integer id ;
    private Integer aid ;
    private Double totalmoney ;
    private Double paymoney ;
    private Double freemoney ;
    private Integer flag ;

}
