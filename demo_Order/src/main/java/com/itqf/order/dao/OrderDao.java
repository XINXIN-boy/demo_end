package com.itqf.order.dao;

import com.itqf.common.dto.OrderAddDto;
import com.itqf.common.dto.OrderSelectDto;
import com.itqf.common.dto.OrderUpdateDto;
import com.itqf.entity.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:54
 * description:
 */
@Repository
public interface OrderDao {

    @Insert("insert into `order`(id,aid,uid,totalmoney ,paymoney , freemoney ,flag,ctime , utime) value (#{id},#{aid},#{uid},#{totalmoney},#{paymoney},#{freemoney},#{flag},now(),now())")
    int insert(Order order);

    @Delete("delete from `order` where id = #{id}")
    int del(int id);

    @Update("update `order` set flag = #{flag} where id = #{id}")
    int updateStatus(OrderUpdateDto dto);

    int updateOrder(OrderUpdateDto dto);

    @Select("select * from `order` where uid = #{uid}")
    List<Order> findALl(int uid);
    @Select("select * from `order` where uid = #{uid} and flag = #{flag}")
    List<Order> findByFlag(OrderSelectDto orderSelectDto);
    @Select("select * from `order` where id = #{id}")
    Order findById(int id);
}
