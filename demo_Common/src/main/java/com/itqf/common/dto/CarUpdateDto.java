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
public class CarUpdateDto {

    private int uid;
    private int skuid;
    private int scount;//+ 正数  - 负数

}
