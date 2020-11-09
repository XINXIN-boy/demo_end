package com.itqf.shopingcar.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.Query;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/911:39
 * description:
 */
@Configuration
@RefreshScope
public class RabbitMQConfig {

    public static String qname_cart="cart-sync";
    @Bean
    public Queue createQu(){
        return new Queue(qname_cart);
    }


}
