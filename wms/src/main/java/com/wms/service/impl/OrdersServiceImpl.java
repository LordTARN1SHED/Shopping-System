package com.wms.service.impl;

import com.wms.entity.OrderBooks;
import com.wms.entity.Orders;
import com.wms.entity.Shoppingcart;
import com.wms.mapper.OrdersMapper;
import com.wms.mapper.ShoppingcartMapper;
import com.wms.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Laplace
 * @since 2024-9-14
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ShoppingcartService shoppingcartService;

    @Override
    public List<Orders> getOrdersByUserId(Integer userid) {
        return ordersMapper.getOrdersByUserId(userid);
    }

    @Override
    public List<OrderBooks> getOrderBooksByOrderId(Integer orderid) {
        return ordersMapper.getOrderBooksByOrderId(orderid);
    }

    @Override
    public boolean updateByOrderid(Integer orderid, Integer payornot){
        return ordersMapper.updateByOrderid(orderid, payornot)>0;
    }

    @Override
    public int addOrder(Orders order){
        int result = ordersMapper.insert(order); // 执行插入操作
        return order.getOrderid();
    }

    @Override
    public boolean addOrderBooks(Integer userid, Integer orderid) {
        List<Shoppingcart> arrayCarts = shoppingcartService.getCartByUserId(userid);

        boolean flag = true;

        for (Shoppingcart cart : arrayCarts) {
            OrderBooks orderBook = new OrderBooks();
            orderBook.setOrderid(orderid);
            orderBook.setBookid(cart.getBookid());
            orderBook.setNum(cart.getNumber());

            System.out.println("Inserting order book: " + orderBook.getOrderid());

            // 逐条插入订单书本
            int result = ordersMapper.insertOrderBook(orderBook.getOrderid(), orderBook.getBookid(), orderBook.getNum());

            if (result <= 0) {
                flag = false;
                break;  // 如果有一次插入失败，则跳出循环
            }
        }

        return flag;
    }
}
