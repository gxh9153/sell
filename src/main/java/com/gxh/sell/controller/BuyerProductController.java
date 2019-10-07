package com.gxh.sell.controller;

import com.gxh.sell.VO.ProductInfoVO;
import com.gxh.sell.VO.ProductVO;
import com.gxh.sell.VO.ResultVO;
import com.gxh.sell.dataobject.ProductCategory;
import com.gxh.sell.dataobject.ProductInfo;
import com.gxh.sell.serviceimpl.CategoryServiceImpl;
import com.gxh.sell.serviceimpl.ProductInfoServiceImpl;
import com.gxh.sell.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public ResultVO list() {


        //测试高并发 创建一个新线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                productInfoService.findUpAll();
            }
        };
        //创建一个固定个数的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(runnable);
        }

        //1查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2查询类目（一次性查询）
        /* 传统方式*/
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }

        /* 精简方式 （java8 lambda表达式） */
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList ){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
               if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                   ProductInfoVO productInfoVO = new ProductInfoVO();
                   BeanUtils.copyProperties(productInfo,productInfoVO);
                   productInfoVOList.add(productInfoVO);
               }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtils.success(productVOList);
    }
}
