package com.itqf.order.service.inte;

import com.itqf.common.dto.OrderAddDto;
import com.itqf.common.dto.OrderGoodsAdd;
import com.itqf.common.dto.OrderSelectDto;
import com.itqf.common.dto.OrderUpdateDto;
import com.itqf.common.vo.JsonResult;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time: 2020/11/513:09
 * description:
 */
public interface OrderService {

    //下单-商品
    JsonResult GoodsAdd(OrderGoodsAdd orderGoodsAdd);

    //添加订单     //下单-购物车  多个商品  的处理
    JsonResult add(OrderAddDto orderAddDto);

    //修改 订单
    JsonResult update(OrderUpdateDto orderUpdateDto);

    //删除 订单
    JsonResult del(int id);

    //查询订单 订单
    JsonResult findAll( int uid );

    //根据状态查询个人的所有订单
    JsonResult findByUidAndFlag( OrderSelectDto orderSelectDto);

    //查询单个的订单
    JsonResult findById( int id );
}
