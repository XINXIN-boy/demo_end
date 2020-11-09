package com.itqf.shopingcar.listener;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.dto.CartMsgDto;
import com.itqf.shopingcar.config.RabbitMQTypeConfig;
import com.itqf.shopingcar.dao.CarDao;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/911:38
 * description: 监听队列 同步数据
 */

@Component
@RabbitListener(queues ="cart-sync")
public class SynDataListener {
    @Autowired
    private CarDao dao;

    @RabbitHandler
    public void handler(CartMsgDto dto){
        if(dto!=null) {
            switch (dto.getType()) {
                case RabbitMQTypeConfig
                        .MQ_CART_ADD:
                    dao.insert((CarAddDto) dto.getData());
                    break;
                case RabbitMQTypeConfig
                        .MQ_CART_UPDATE:
                    dao.update((CarUpdateDto) dto.getData());
                    break;
            }
        }
    }
}