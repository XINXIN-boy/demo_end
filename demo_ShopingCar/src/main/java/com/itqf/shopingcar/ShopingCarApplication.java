package com.itqf.shopingcar;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/411:15
 * description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.itqf.shopingcar.dao")
@EnableTransactionManagement
public class ShopingCarApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopingCarApplication.class,args) ;
    }
}
