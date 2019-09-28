package com.gxh.sell.serviceimpl;

import com.gxh.sell.converter.OrderMaster2OrderDTOConverter;
import com.gxh.sell.dataobject.OrderDetail;
import com.gxh.sell.dataobject.OrderMaster;
import com.gxh.sell.dataobject.ProductInfo;
import com.gxh.sell.dto.CartDTO;
import com.gxh.sell.dto.OrderDTO;
import com.gxh.sell.enums.OrderStatusEnums;
import com.gxh.sell.enums.PayStatusEnums;
import com.gxh.sell.enums.ResultEnums;
import com.gxh.sell.exceptions.SellException;
import com.gxh.sell.repository.OrderDetailRepository;
import com.gxh.sell.repository.OrderMasterRepository;
import com.gxh.sell.service.OrderService;
import com.gxh.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoServiceImpl  productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtils.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
//        List<CartDTO> cartDTOList = new ArrayList<>();
        //1 查询商品（数量，价格）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIT);
            }
            //2 计算订单总价
            orderAmount = productInfo.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库(orderDetail)
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }

        //3 写入订单数据库(orderMaster)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnums.ORDER_DETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //查看订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确 ，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】更新订单失败, orderMaster={}",orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_ERROR);
        }

        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情,orderDTO={}",orderDTO);
            throw new SellException(ResultEnums.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果订单已支付，则退款
        if(orderDTO.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //查看订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【订单完结】订单状态不正确 orderId={},orderStatus = {}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnums.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【订单完结】订单更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //查看订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【支付订单】订单状态不正确 orderId={},orderStatus = {}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //查看支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("【支付订单】支付状态不正确 orderId={}, payStatus",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【支付订单】订单更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
