package com.itqf.order.listener;

import com.itqf.common.thrid.RedissonUtil;
import com.itqf.common.vo.RedisKeyConfig;
import com.itqf.entity.entity.Order;
import com.itqf.entity.entity.Orderitem;
import com.itqf.order.dao.OrderDao;
import com.itqf.order.dao.OrderitemDao;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/522:11
 * description:
 */
@Component
@RabbitListener(queues = "cloud-order-sync")
public class OrderSynListener {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderitemDao orderitemDao;

    @RabbitHandler
    @Transactional
    public void handler(int id){
        String k1= RedisKeyConfig.ORDER_V2+id;
        String k2=RedisKeyConfig.ORDERITEM_V2+id;

        if(RedissonUtil.checkKey(k1,k2)){

            Order order= (Order) RedissonUtil.getStrObj(k1);

            orderDao.insert(order);

            Collection<Object> items= RedissonUtil.getHash(k2);

            ArrayList<Orderitem> list = new ArrayList<>();
            for (Object obj : items) {
               Orderitem orderitem =  (Orderitem)obj;
               list.add(orderitem);
            }

            orderitemDao.insertBatch(list);
        }
    }
}
