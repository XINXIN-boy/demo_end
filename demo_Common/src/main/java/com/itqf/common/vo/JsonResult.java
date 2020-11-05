package com.itqf.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/59:38
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {

    private int code ;
    private String msg ;
    private T data ;
    private long count ;


    public static JsonResult success(){
        return new JsonResult(0,"success" , null ,0l);
    }
    public static JsonResult success(String msg){
        return new JsonResult(0,msg , null ,0l);
    }
    public static <T>JsonResult success(T data){
        return new JsonResult(0,"success" , data ,0l);
    }
    public static <T> JsonResult success(T data ,long count){
        return new JsonResult(0,"success" , data ,count);
    }


    public static JsonResult fail(){
        return new JsonResult(1,"fail" , null ,0l);
    }
    public static JsonResult fail(String msg){
        return new JsonResult(1,msg , null ,0l);
    }

}
