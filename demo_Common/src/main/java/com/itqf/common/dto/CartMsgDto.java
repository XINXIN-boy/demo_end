package com.itqf.common.dto;

import lombok.Data;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/917:17
 * description:
 */
@Data
public class CartMsgDto {
    private long id;//唯一id
    private int type;//消息类型
    private Object data;//消息的内容
}
