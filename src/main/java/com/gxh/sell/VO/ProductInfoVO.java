package com.gxh.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 返回给客户的商品详情对象
 */

@Data
public class ProductInfoVO {

    @JsonProperty("id") //对象序列化后返回给前端的名字
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

}
