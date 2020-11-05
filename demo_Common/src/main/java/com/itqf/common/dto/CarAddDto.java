package com.itqf.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:53
 * description:
 */
@Data
@AllArgsConstructor
public class CarAddDto {

    private Integer skuid ;
    private Integer uid ;
    private Integer scount ;  //计数
    private Integer jprice ;  //价格


}
