package com.gxh.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity //将数据库映射成对象
@DynamicUpdate//动态更新数据库的修改时间
public class ProductCategory {

    /**类目id */
    @Id // 表示categoryId为主键
    @GeneratedValue //表示主键自增
    private Integer categoryId;

    /**类目名称 */
    private String categoryName;

    /**类目编号 */
    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
