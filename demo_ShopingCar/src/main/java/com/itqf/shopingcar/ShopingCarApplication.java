package com.itqf.shopingcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/411:15
 * description:
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class ShopingCarApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopingCarApplication.class,args) ;
    }
}
