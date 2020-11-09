package com.itqf.shopingcar.service.impl;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarDelDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.vo.JsonResult;
import com.itqf.entity.entity.ShopingCar;
import com.itqf.shopingcar.dao.CarDao;
import com.itqf.shopingcar.service.inte.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:48
 * description:
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao carDao ;

    @Override
    public JsonResult add(CarAddDto dto) {
        //校验
        if(dto!=null){
            if(dto.getSkuid()>0 && dto.getSkuid()>0 && dto.getJprice()>0 && dto.getUid()>0){
                ShopingCar cart=carDao.findByUid(dto.getUid());
                if(cart==null){
                    //第一次
                    if(carDao.insert(dto)>0){
                        return JsonResult.success();
                    }
                }else{
                    CarUpdateDto carUpdateDto = new CarUpdateDto(cart.getUid(),cart.getSkuid(), dto.getScount());
                    //之前添加
                    if(carDao.update(carUpdateDto)>0){
                        return JsonResult.success();
                    }
                }
            }
        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult del(CarDelDto dto) {
        if (dto!= null && dto.getSkuid()>0 && dto.getUid() >0){
            if (carDao.del(dto)>0) {
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult plus(CarUpdateDto dto) {
        if (dto!= null && dto.getSkuid()>0 && dto.getUid() >0){
            if (carDao.update(dto)>0) {
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult subtract(CarUpdateDto dto) {
        if (dto!= null && dto.getUid()>0 && dto.getSkuid() >0){
            //参数正确
            if (carDao.update(dto)>0) {
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }
}
