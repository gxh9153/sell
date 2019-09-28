package com.gxh.sell.dataobject;

import com.gxh.sell.enums.OrderStatusEnums;
import com.gxh.sell.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /**订单id */
    @Id
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
    private Integer orderStatus= OrderStatusEnums.NEW.getCode();

    /**支付状态 */
    private Integer payStatus= PayStatusEnums.WAIT.getCode();

    /**创建时间 */
    private Date createTime;

    /**更新时间 */
    private Date updateTime;
}
