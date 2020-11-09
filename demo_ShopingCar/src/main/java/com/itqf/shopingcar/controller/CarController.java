package com.itqf.shopingcar.controller;

import com.itqf.common.dto.CarAddDto;
import com.itqf.common.dto.CarUpdateDto;
import com.itqf.common.vo.JsonResult;
import com.itqf.shopingcar.service.inte.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/910:01
 * description:
 */
@Api(tags = "购物车的接口文档")
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    @Qualifier(value = "carServiceImpl2")
    private CarService carService ;

    @ApiOperation(value = "添加购物车")
    @PostMapping("/save.do")
    public JsonResult save(@RequestBody CarAddDto dto){
        return carService.add(dto) ;
    }

    @ApiOperation(value = "加")
    @GetMapping("/plus.do")
    public JsonResult plus(int id){
        return carService.plus(id) ;
    }

    @ApiOperation(value = "减")
    @GetMapping("/subtract.do")
    public JsonResult subtract(int id){
        return carService.subtract(id) ;
    }

    @ApiOperation(value = "删除购物车")
    @DeleteMapping("/del.do")
    public JsonResult del(int id){
        return carService.del(id) ;
    }

}
