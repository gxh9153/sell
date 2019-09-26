package com.gxh.sell.repository;

import com.gxh.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {


    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory one = repository.findOne(1);
        System.out.println(one.toString());
    }
    @Test
    @Transactional //事务完全回滚
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("女生最爱",3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null,result);
    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(null,result.size());
    }
}