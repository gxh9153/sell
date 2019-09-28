package com.gxh.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gxh.sell.dataobject.OrderDetail;
import com.gxh.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /**订单id */
    private String OrderId;

    /**买家名称 */
    private String buyerName;

    /**买家电话 */
    private String buyerPhone;

    /**买家地址 */
    private String buyerAddress;

    /**买家openid */
    private String buyerOpenid;

    /**订单总金额 */
    private BigDecimal orderAmount;

    /**订单状态 */
    private Integer orderStatus;

    /**支付状态 */
    private Integer payStatus;

    /**创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 订单详情 */

    List<OrderDetail> orderDetailList;
}
