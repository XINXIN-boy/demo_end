package com.itqf.common.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddDto {

    private Integer aid ;
    private Integer uid ;
    private Double totalmoney ;
    private Double paymoney ;
    private Double freemoney ;

}
