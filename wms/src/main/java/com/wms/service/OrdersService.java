package com.wms.service;

import com.wms.entity.OrderBooks;
import com.wms.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Laplace
 * @since 2024-9.14
 */
public interface OrdersService extends IService<Orders> {

    public List<Orders> getOrdersByUserId(Integer userid);

    public List<OrderBooks> getOrderBooksByOrderId(Integer orderid);

    public int addOrder(Orders order);

    public boolean addOrderBooks(Integer userid, Integer orderid);

    public boolean updateByOrderid(Integer orderid, Integer payornot);
}
