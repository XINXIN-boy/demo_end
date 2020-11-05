package com.itqf.shopingcar.service.inte;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.vo.JsonResult;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/417:00
 * description:
 */
public interface CarService {

    //实现购物车的添加
    JsonResult add(CarAddDto dto);
    //购物车的删除
    JsonResult del (int id);
    //购物车数量的+
    JsonResult plus(CarUpdateDto dto);
    //购物车数量的—
    JsonResult subtract(CarUpdateDto dto);


}
