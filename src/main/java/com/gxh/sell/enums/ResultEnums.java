package com.gxh.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {
    PARAM_ERROR(1,"表单参数不正确"),
    PRODUCT_NOT_EXIT(10,"该商品不存在"),
    PRODUCT_STOCK_ERROR(11,"该商品库存不足"),
    ORDER_NOT_EXIT(12,"该订单不存在"),
    ORDER_DETAIL_NOT_EXIT(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_UPDATE_ERROR(15,"更新订单失败"),
    ORDER_DETAIL_EMPTY(16,"订单中无商品"),
    ORDER_PAY_STATUS_ERROR(17,"支付状态错误"),
    CART_EMPTY(18,"购物车为空"),
    OPENID_EMPTY(19,"微信openid为空"),
    ORDER_OWNER_ERROR(20,"该订单不属于当前用户"),
    ;
    private Integer code;
    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
