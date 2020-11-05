package com.itqf.shopingcar.service.impl;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.vo.JsonResult;
import com.itqf.shopingcar.service.inte.CarService;
import org.springframework.stereotype.Service;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/511:48
 * description:
 */
@Service
public class CarServiceImpl implements CarService {
    @Override
    public JsonResult add(CarAddDto dto) {
        return null;
    }

    @Override
    public JsonResult del(int id) {
        return null;
    }

    @Override
    public JsonResult plus(CarUpdateDto dto) {
        return null;
    }

    @Override
    public JsonResult subtract(CarUpdateDto dto) {
        return null;
    }
}
