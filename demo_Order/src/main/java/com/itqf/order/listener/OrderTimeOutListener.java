package com.itqf.order.listener;

import com.itqf.common.common.OrderFlag;
import com.itqf.common.dto.OrderUpdateDto;
import com.itqf.entity.entity.Order;
import com.itqf.order.dao.OrderDao;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/522:24
 * description:
 */
@Component
@RabbitListener(queues = "cloud-order-timeout")
public class OrderTimeOutListener {

    @Autowired
    private OrderDao orderDao;

    @RabbitHandler
    @Transactional
    public void handler(int id){
        Order order=orderDao.findById(id);
        if(order!=null){
            //订单超时
            //更改订单状态
            orderDao.updateStatus(new OrderUpdateDto(id, OrderFlag.超时订单.getVal()));
            //释放库存
            //释放积分抵扣
            //释放优惠卷
            //释放满减
        }
    }
}
