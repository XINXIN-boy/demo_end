package com.itqf.common.dto;

import lombok.Data;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/512:58
 * description:
 */
@Data
public class OrderitemAddDto {
    private Integer oid ;
    private Integer skuid ;
    private Integer scount ;
    private Double price ;

}
