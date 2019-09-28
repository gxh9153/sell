package com.gxh.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnums {

    NEW(0,"新下单"),
    FINISH(1,"订单完成"),
    CANCEL(2,"取消订单")
    ;

    private Integer code;
    private String message;

    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
