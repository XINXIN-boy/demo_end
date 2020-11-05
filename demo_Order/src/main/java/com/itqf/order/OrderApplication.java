package com.itqf.order;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:53
 * description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.itqf.order.dao")
@EnableTransactionManagement  //开启事务
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }
}
