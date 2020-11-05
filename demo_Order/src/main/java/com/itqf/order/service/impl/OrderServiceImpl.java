package com.itqf.order.service.impl;

import com.itqf.common.common.OrderFlag;
import com.itqf.common.vo.RedisKeyConfig;
import com.itqf.common.dto.*;
import com.itqf.common.thrid.RedissonUtil;
import com.itqf.common.vo.JsonResult;
import com.itqf.entity.entity.Order;
import com.itqf.entity.entity.Orderitem;
import com.itqf.order.dao.OrderDao;
import com.itqf.order.dao.OrderitemDao;
import com.itqf.order.service.inte.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/513:14
 * description:
 * 考虑到购物 可能
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao ;

    @Autowired
    private OrderitemDao orderitemDao ;


    /**
     * 商品页面添加商品   单个商品
     * 可以添加分布式锁   分布式锁  咋添加  其实就是在redis 中添加了一个字段 只要这个字段存在 别的线程就不能执行
     * @param orderGoodsAdd
     * @return
     */
    @Override
    @Transactional
    public JsonResult GoodsAdd(OrderGoodsAdd orderGoodsAdd) {
        Random random = new Random();
        //校验参数
        if (orderGoodsAdd!= null && orderGoodsAdd.getAid()>0 && orderGoodsAdd.getSkuid()>0 && orderGoodsAdd.getScount() >0 && orderGoodsAdd.getUid()>0){
            //生成订单  完善订单的信息 id通过生成器生成  在这里可以先随机一个  还有总价  优惠价  最后支付的价格
            Order order = new Order("ZK" + System.currentTimeMillis() ,orderGoodsAdd.getAid(),orderGoodsAdd.getUid(), OrderFlag.待支付.getVal());
            //对商品集合进行遍历 从数据库中将商品的信息拿出来  在这里进行模拟
            Double tm = 0.0 ;
            try {
                //加个锁
                RedissonUtil.lock(RedisKeyConfig.ORDER_LOCK + orderGoodsAdd.getSkuid(), RedisKeyConfig.LOCK_TIME);

                //可以根据skuid 得到商品信息  得到商品的单价   在这里随机一个单价
                Double pr =Math.floor( random.nextDouble()*1000);
                //考虑到可能存在超卖现像  在这里应该再判断一下商品的库存信息，看是不是满足条件  库存的数量应该根据 skuid 去查询数据库 在这里先用随机的做出判断
                Orderitem orderitem = null ;
                if (orderGoodsAdd.getScount() < Math.floor( random.nextDouble()*50)){
                    //商品的数量 小于库存数量
                    //计算总的价格
                    tm += pr*orderGoodsAdd.getScount();
                    //将商品详情添加到集合中
                    orderitem = new Orderitem(order.getId(), orderGoodsAdd.getSkuid(), orderGoodsAdd.getScount(), pr);
                }else {
                    return JsonResult.fail("库存不足");
                }
                //有了总的价格  需要计算优惠的价格
                //调用优惠服务  会员服务  红包服务  满减  积分抵扣
                //得到优惠的金额
                Double fm = Math.floor( random.nextDouble()*100);
                //为了避免优惠的金额大于 总价
                if (fm >= tm){
                    fm = tm ;
                }
                //得到总的需要支付的金额
                Double pm = tm - fm ;
                //完善订单
                order.setTotalmoney(tm);
                order.setFreemoney(fm);
                order.setPaymoney(pm);
                //订单完善结束  给数据库添加订单
                if (orderDao.insert(order)>0){
                    //添加订单详情
                    if (orderitemDao.insert(orderitem)>0){
                        //修改商品的库存
                        return JsonResult.success();
                    }else {
                        return JsonResult.fail("系统异常，稍后再试") ;
                    }
                }else {
                    return JsonResult.fail("系统异常，稍后再试") ;
                }
            } finally {
                //执行结束释放锁
                RedissonUtil.unlock(RedisKeyConfig.ORDER_LOCK+orderGoodsAdd.getSkuid());
            }
        }else {
            return JsonResult.fail("参数不合法") ;
        }
    }

    /**
     * 购物车添加商品
     * 考虑到可能多个线程访问的情况下 商品的库存可能会出现超卖的情况  给订单的的生成过程加一把锁
     * 可以加同步锁  但是同步锁是单机锁 只能一个服务器的情况下执行  当然在单机锁的情况下可以 给 修改库存的过程 加锁 这样即保证了库存的数量  也保证了系统的性能
      * 因此用分布式锁
     * @param orderAddDto
     * @return
     */
    @Override
    @Transactional
    public JsonResult add(OrderAddDto orderAddDto) {
        Random random = new Random();
        //先将所有的 商品拿出
        List<GoodsDto> list = orderAddDto.getList();
        //校验参数
        if (orderAddDto!= null && orderAddDto.getUid()>0 && orderAddDto.getAid()>0 && list !=null && list.size()>0){
            //生成订单  完善订单的信息 id通过生成器生成  在这里可以先随机一个  还有总价  优惠价  最后支付的价格
            Order order = new Order("ZK" + System.currentTimeMillis() ,orderAddDto.getAid(),orderAddDto.getUid(), OrderFlag.待支付.getVal());
            //对商品集合进行遍历 从数据库中将商品的信息拿出来  在这里进行模拟
            ArrayList<Orderitem> orderitems = new ArrayList<>();
            Double tm = 0.0 ;
            try {
                //加个锁
                RedissonUtil.lock(RedisKeyConfig.ORDER_LOCK + orderAddDto.getSkuids(), RedisKeyConfig.LOCK_TIME);
                for (GoodsDto goodsDto : list) {
                    //可以根据skuid 得到商品信息  得到商品的单价   在这里随机一个单价
                    Double pr =Math.floor( random.nextDouble()*1000);
                    //考虑到可能存在超卖现像  在这里应该再判断一下商品的库存信息，看是不是满足条件  库存的数量应该根据 skuid 去查询数据库 在这里先用随机的做出判断
                    if (goodsDto.getScount() < Math.floor( random.nextDouble()*50)){
                        //商品的数量 小于库存数量
                        //计算总的价格
                        tm += pr*goodsDto.getScount();
                        //将商品详情添加到集合中
                        orderitems.add(new Orderitem(order.getId(),goodsDto.getSkuid(),goodsDto.getScount(),pr));
                    }else {
                        return JsonResult.fail("库存不足");
                    }

                }
                //有了总的价格  需要计算优惠的价格
                //调用优惠服务  会员服务  红包服务  满减  积分抵扣
                //得到优惠的金额
                Double fm = Math.floor( random.nextDouble()*100);
                //为了避免优惠的金额大于 总价
                if (fm >= tm){
                    fm = tm ;
                }
                //得到总的需要支付的金额
                Double pm = tm - fm ;
                //完善都订单
                order.setTotalmoney(tm);
                order.setFreemoney(fm);
                order.setPaymoney(pm);
                //订单完善结束  给数据库添加订单
                if (orderDao.insert(order)>0){
                    //添加订单详情
                    if (orderitemDao.insertBatch(orderitems)>0){
                        //修改商品的库存
                        return JsonResult.success();
                    }else {
                        return JsonResult.fail("系统异常，稍后再试") ;
                    }
                }else {
                    return JsonResult.fail("系统异常，稍后再试") ;
                }
            } finally {
                //执行结束释放锁
                RedissonUtil.unlock(RedisKeyConfig.ORDER_LOCK+orderAddDto.getSkuids());
            }
        }else {
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
