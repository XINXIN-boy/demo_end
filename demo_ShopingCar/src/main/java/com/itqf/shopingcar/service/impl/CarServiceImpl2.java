package com.itqf.shopingcar.service.impl;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.vo.JsonResult;
import com.itqf.entity.entity.ShopingCar;
import com.itqf.shopingcar.dao.CarDao;
import com.itqf.shopingcar.service.inte.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:48
 * description:
 */
@Service
public class CarServiceImpl2 implements CarService {

    @Autowired
    private CarDao carDao ;

    @Override
    public JsonResult add(CarAddDto dto) {
        //校验
        if(dto!=null){
            if(dto.getSkuid()>0 && dto.getSkuid()>0 && dto.getJprice()>0 && dto.getUid()>0){
                ShopingCar cart=carDao.findByUid(dto);
                if(cart==null){
                    //第一次
                    if(carDao.insert(dto)>0){
                        return JsonResult.success();
                    }
                }else{
                    CarUpdateDto carUpdateDto = new CarUpdateDto(cart.getId(), dto.getScount());
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
    public JsonResult del(int id) {
        if (id >0){
            if (carDao.del(id)>0) {
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult plus(int id) {
        if (id>0){
            //参数正确
            CarUpdateDto carUpdateDto = new CarUpdateDto(id, 1);
            if (carDao.update(carUpdateDto)>0) {
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }

    @Override
    public JsonResult subtract(int id) {
        if (id>0){
            //参数正确
            CarUpdateDto carUpdateDto = new CarUpdateDto(id, -1);
            if (carDao.update(carUpdateDto)>0) {
                return JsonResult.success();
            }
        }
        return JsonResult.fail();
    }
}
