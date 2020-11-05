package com.itqf.order;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:53
 * description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.itqf.order.dao")
public class OrderApplication {
}
