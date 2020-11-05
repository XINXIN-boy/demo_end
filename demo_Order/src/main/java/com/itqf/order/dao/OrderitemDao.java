package com.itqf.order.dao;

import com.itqf.common.dto.*;
import com.itqf.entity.entity.Order;
import com.itqf.entity.entity.Orderitem;
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
public interface OrderitemDao {

    @Insert("insert into orderitem (oid , skuid ,scount ,price ,ctime) value (#{oid},#{skuid},#{scount},#{price},now())")
    int insert(Orderitem orderitem);

    int insertBatch(List<Orderitem> list);

    @Delete("delete from orderitem where id = #{id}")
    int del(int id);

    int update(OrderitemUpdateDto orderitemUpdateDto);


    @Select("select * from orderitem where oid = #{oid}")
    List<Orderitem> findALl(int oid);

    @Select("select * from orderitem where id = #{id}")
    List<Orderitem> findById(int id);






}
