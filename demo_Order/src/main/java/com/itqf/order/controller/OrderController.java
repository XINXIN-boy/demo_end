package com.itqf.order.controller;

import com.itqf.common.dto.OrderAddDto;
import com.itqf.common.dto.OrderGoodsAdd;
import com.itqf.common.vo.JsonResult;
import com.itqf.order.service.inte.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/520:23
 * description:
 */
@Api(tags = "订单的接口文档")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService ;

    @ApiOperation(value = "商品页添加")
    @PostMapping("/addGoods.do")
    public JsonResult addGoods(@RequestBody OrderGoodsAdd orderGoodsAdd){
        return orderService.GoodsAdd(orderGoodsAdd) ;
    }

    @ApiOperation(value = "购物车添加")
    @PostMapping("/add.do")
    public JsonResult add(@RequestBody OrderAddDto orderAddDto){
        return orderService.add(orderAddDto) ;
    }


}
