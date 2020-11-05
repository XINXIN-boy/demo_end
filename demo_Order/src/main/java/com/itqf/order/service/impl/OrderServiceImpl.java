package com.itqf.order.service.impl;

import com.itqf.common.dto.OrderAddDto;
import com.itqf.common.dto.OrderSelectDto;
import com.itqf.common.dto.OrderUpdateDto;
import com.itqf.common.vo.JsonResult;
import com.itqf.entity.entity.Order;
import com.itqf.entity.entity.Orderitem;
import com.itqf.order.dao.OrderDao;
import com.itqf.order.dao.OrderitemDao;
import com.itqf.order.service.inte.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/513:14
 * description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao ;


    @Autowired
    private OrderitemDao orderitemDao ;


    @Override
    public JsonResult add(OrderAddDto orderAddDto) {
        //校验参数
        if (orderAddDto != null && orderAddDto.getAid()>0 && orderAddDto.getFreemoney()>0
                && orderAddDto.getPaymoney()>0&& orderAddDto.getTotalmoney()>0 && orderAddDto.getUid()>0
                && orderAddDto.getScount()>0  && orderAddDto.getSkuid()>0 )
        {
            //参数合法
            //添加数据到数据库 首先补全参数
            Order order = new Order(orderAddDto.getAid(),orderAddDto.getUid(),
                    orderAddDto.getTotalmoney(),orderAddDto.getPaymoney(),orderAddDto.getFreemoney(),1);

            if (orderDao.insert(order)>0){
                //添加成功
                //添加订单详情  根据skuid 去获取商品的价格
                Orderitem orderitem = new Orderitem(order.getId(),orderAddDto.getSkuid(),orderAddDto.getScount()
                ,800.0);
                if (orderitemDao.insert(orderitem)>0){
                    //订单详情添加成功
                    return JsonResult.success();
                }else {
                    //添加失败
                    return JsonResult.fail("系统异常，稍后再试") ;
                }
            }else {
                //添加失败
                return JsonResult.fail("系统异常，稍后再试") ;
            }
        }else {
            //参数不合法
            return JsonResult.fail("参数不合法") ;
        }
    }

    @Override
    public JsonResult update(OrderUpdateDto orderUpdateDto) {
        //参数的校验
        if (orderUpdateDto != null && orderUpdateDto.getAid()>0
        && orderUpdateDto.getFlag()>0 && orderUpdateDto.getId()>0  && orderUpdateDto.getFreemoney()>0&& orderUpdateDto.getPaymoney()>0&& orderUpdateDto.getTotalmoney()>0)
        {
            //参数正确
            //修改信息
            if (orderDao.updateOrder(orderUpdateDto)>0){
                //修改成功
                return JsonResult.success();
            }else {
                return JsonResult.fail("修改失败");
            }
        }else{
            return JsonResult.fail("参数不合法");
        }
    }

    @Override
    public JsonResult del(int id) {
        if (id > 0 ){
           if (orderDao.del(id)>0){
               return JsonResult.success();
           }else {
               return JsonResult.fail("删除失败，稍后再试");
           }
        }else{
            return JsonResult.fail("参数不合法");
        }
    }

    @Override
    public JsonResult findAll(int uid) {
        if (uid > 0 ){
            List<Order> list = orderDao.findALl(uid);
            if (list != null && list.size()>0){
                return JsonResult.success(list);
            }else {
                return JsonResult.fail("未查询到数据");
            }
        }else{
            return JsonResult.fail("参数不合法");
        }
    }

    @Override
    public JsonResult findByUidAndFlag(OrderSelectDto orderSelectDto) {
        if (orderSelectDto != null && orderSelectDto.getFlag()>0 && orderSelectDto.getUid()>0){
            List<Order> list = orderDao.findByFlag(orderSelectDto);
            if (list != null && list.size()>0){
                return JsonResult.success(list);
            }else {
                return JsonResult.fail("未查询到数据");
            }
        }else{
            return JsonResult.fail("参数不合法");
        }
    }


    @Override
    public JsonResult findById(int id) {
        if (id > 0 ){
            Order order = orderDao.findById(id);
            if (order != null){
                return JsonResult.success(order);
            }else {
                return JsonResult.fail("未查询到数据");
            }
        }else{
            return JsonResult.fail("参数不合法");
        }
    }
}
