package com.itqf.shopingcar.dao;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarDelDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.entity.entity.ShopingCar;
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
 * time: 2020/11/99:07
 * description:
 */
@Repository
public interface CarDao {

    @Insert("insert into car (skuid , uid , scount , jprice,ctime) value (#{skuid},#{uid},#{scount},#{jprice},now())")
    int insert (CarAddDto dto) ;

    @Delete("delete from car where uid =#{uid} and skuid = #{skuid}")
    int del(CarDelDto dto);

    @Update("update car set scount = scount + #{scount} where uid = #{uid} and skuid = #{skuid}")
    int update(CarUpdateDto dto);

    @Select("select * from car where uid = #{uid} limit 1")
    ShopingCar findByUid(int uid);

    @Select("select * from car where uid = #{uid}")
    List<ShopingCar> findAllByUid(Integer uid);
}
