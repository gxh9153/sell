package com.gxh.sell.serviceimpl;

import com.gxh.sell.dataobject.ProductInfo;
import com.gxh.sell.dto.CartDTO;
import com.gxh.sell.enums.ProductStatusEnums;
import com.gxh.sell.enums.ResultEnums;
import com.gxh.sell.exceptions.SellException;
import com.gxh.sell.repository.ProductInfoRepository;
import com.gxh.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public  List<ProductInfo> findUpAll() {

        List<ProductInfo> productInfoList = repository.findByProductStatus(ProductStatusEnums.UP.getCode());
        return productInfoList;
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIT);
            }
            int result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIT);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result<0){
                throw  new SellException(ResultEnums.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

}
