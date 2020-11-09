package com.itqf.shopingcar.service.impl;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarDelDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.dto.CartMsgDto;
import com.itqf.common.thrid.RedissonUtil;
import com.itqf.common.uitls.IdGeneratorSinglon;
import com.itqf.common.vo.JsonResult;
import com.itqf.common.vo.RedisKeyConfig;
import com.itqf.entity.entity.ShopingCar;

import com.itqf.shopingcar.config.RabbitMQConfig;
import com.itqf.shopingcar.config.RabbitMQTypeConfig;
import com.itqf.shopingcar.dao.CarDao;
import com.itqf.shopingcar.service.inte.CarService;
import org.redisson.Redisson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:48
 * description:
 */
@Service
public class CarServiceImpl2 implements CarService {

    @Autowired
    private CarDao carDao ;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加购物车
     * @param dto
     * @return
     */
    @Override
    public JsonResult add(CarAddDto dto) {
        //校验
        if(dto!=null){
            if(dto.getSkuid()>0 && dto.getSkuid()>0 && dto.getJprice()>0 && dto.getUid()>0) {
                //首先判断有没有k的存在
                String key = RedisKeyConfig.CART_KEY +dto.getUid();
                CartMsgDto cartMsgDto = new CartMsgDto();
                if (RedissonUtil.checkKey(key)){
                    //存在key  之前添加过  再判断skuid存在
                    if (RedissonUtil.checkField(key,dto.getSkuid()+"")){
                        //存在  修改数量  先获取
                        ShopingCar cart = (ShopingCar) RedissonUtil.getHash(key, dto.getSkuid() + "");
                        //修改数量
                        cart.setScount(cart.getScount()+dto.getScount());
                        //将其更新到redis
                        RedissonUtil.setHash(key,dto.getSkuid()+"",cart);
                        //修改Mq的类型
                        cartMsgDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);

                    }else {
                        //不存在 新加
                        ShopingCar shopingCar = new ShopingCar(dto.getSkuid(), dto.getUid(), dto.getScount(), dto.getJprice());
                        RedissonUtil.setHash(key,dto.getSkuid()+"",shopingCar);
                        //设置MQ的类型
                        cartMsgDto.setType(RabbitMQTypeConfig.MQ_CART_ADD);
                    }
                }else {
                    //不存在  之前未添加  或者key 失效了 先检查一下数据库存在不  用来判断是失效还是第一次添加
                    List<ShopingCar> list = carDao.findAllByUid(dto.getUid());
                    if (list!=null){
                        //存在购物车  拿出所有的  将其同步到redis
                        HashMap<String, Object> map = new HashMap<>();
                        boolean re = true ;

                        for (ShopingCar shopingCar : list) {
                            //判断当前商品是否存在
                            if (shopingCar.getSkuid() == dto.getSkuid()){
                                //当前商品存在
                                //修改数量
                                shopingCar.setScount(shopingCar.getScount()+dto.getScount());
                                //修改Mq的类型
                                cartMsgDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                                //不需要添加新的购物车
                                re = false ;
                            }
                            //将购物车信息添加到map
                            map.put(dto.getSkuid()+"" , shopingCar);
                        }
                        //是否添加新的商品
                        if (re){
                            //添加
                            map.put(dto.getSkuid()+"" ,new ShopingCar(dto.getSkuid(), dto.getUid(), dto.getScount(), dto.getJprice()));
                            //修改Mq的类型
                            cartMsgDto.setType(RabbitMQTypeConfig.MQ_CART_ADD);
                        }
                        //将所有购物车的信息同步到redis
                        RedissonUtil.setHashAll(key,map);
                    }else {
                        //不存在  说明之前未添加 首次添加
                        ShopingCar shopingCar = new ShopingCar(dto.getSkuid(), dto.getUid(), dto.getScount(), dto.getJprice());
                        RedissonUtil.setHash(key,dto.getSkuid()+"",shopingCar);
                        //设置MQ的类型
                       cartMsgDto.setType(RabbitMQTypeConfig.MQ_CART_ADD);
                    }
                    //设置有效期
                    RedissonUtil.setTime(key,RedisKeyConfig.CART_TIME, TimeUnit.HOURS);
                }
                //发送MQ  需要一个唯一的id
                cartMsgDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());
                cartMsgDto.setData(dto);
                //MQ发送消息
                rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_cart,cartMsgDto);
                return JsonResult.success();
            }else {
                return JsonResult.fail("参数异常");
            }
        }else {
            return JsonResult.fail("参数异常");
        }
    }

    @Override
    public JsonResult del(CarDelDto dto) {
        if (dto!= null && dto.getSkuid()>0 && dto.getUid() >0){

            if (RedissonUtil.checkKey(RedisKeyConfig.CART_KEY+dto.getUid())){
                //用户存在
                RedissonUtil.delHash(RedisKeyConfig.CART_KEY+dto.getUid(),dto.getSkuid()+"");
                if (carDao.del(dto)>0){
                    return JsonResult.success();
                }
            }

        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult plus(CarUpdateDto dto) {
        String k=RedisKeyConfig.CART_KEY+dto.getUid();
        if(RedissonUtil.checkKey(k)){
            if(RedissonUtil.checkField(k,dto.getSkuid()+"")) {
                //加  修改redis
                ShopingCar cart = (ShopingCar) RedissonUtil.getHash(k, dto.getSkuid() + "");
                cart.setScount(cart.getScount() - dto.getScount());
                RedissonUtil.setHash(k, dto.getSkuid() + "", cart);
                //发送队列消息 更新mysql
                CartMsgDto msgDto=new CartMsgDto();
                msgDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                msgDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());

                msgDto.setData(dto);
                rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_cart,msgDto);
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult subtract(CarUpdateDto dto) {
        String k=RedisKeyConfig.CART_KEY+dto.getUid();
        if(RedissonUtil.checkKey(k)){
            if(RedissonUtil.checkField(k,dto.getSkuid()+"")) {
                //减  修改redis
                ShopingCar cart = (ShopingCar) RedissonUtil.getHash(k, dto.getSkuid() + "");
                cart.setScount(cart.getScount() + dto.getScount());
                RedissonUtil.setHash(k, dto.getSkuid() + "", cart);
                //发送队列消息 更新mysql
                CartMsgDto msgDto=new CartMsgDto();
                msgDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                msgDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());
                dto.setScount(-dto.getScount());
                msgDto.setData(dto);
                rabbitTemplate.convertAndSend("", RabbitMQConfig.qname_cart,msgDto);
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }

}
