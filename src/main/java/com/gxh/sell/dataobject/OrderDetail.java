package com.gxh.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail {

    /**订单id */
    @Id
    private String detailId;

    /**订单id */
    private String orderId;

    /**订单id */
    private String productId;

    /**订单id */
    private String productName;

    /**订单id */
    private BigDecimal productPrice;

    /**订单id */
    private Integer productQuantity;

    /**订单id */
    private String productIcon;

}
