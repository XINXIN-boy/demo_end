package com.itqf.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/512:46
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateDto {

    private Integer id ;
    private Integer aid ;
    private Double totalmoney ;
    private Double paymoney ;
    private Double freemoney ;
    private Integer flag ;

    public OrderUpdateDto(Integer id, Integer flag) {
        this.id = id;
        this.flag = flag;
    }
}
