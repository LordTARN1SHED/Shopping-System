package com.wms.controller;

import com.wms.common.Result;
import com.wms.entity.OrderBooks;
import com.wms.entity.Orders;
import com.wms.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Laplace
 * @since 2024-9-14
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/list/{userid}")
    public Result getOrdersByUserId(@PathVariable Integer userid) {
        List<Orders> items = ordersService.getOrdersByUserId(userid);
        return Result.suc(items);
    }

    @PostMapping("/update/{orderid}/{payornot}")
    public Result update(@PathVariable Integer orderid, @PathVariable Integer payornot){
        return ordersService.updateByOrderid(orderid, payornot)?Result.suc():Result.fail();
    }

    @PostMapping("/save")
    public Result addOrder(@RequestBody Orders order) {
        int orderid = ordersService.addOrder(order);
        return ordersService.addOrderBooks(order.getUserid(), orderid)?Result.suc():Result.fail();
    }

    @RequestMapping("/orderbook/{orderid}")
    public Result getBookById(@PathVariable Integer orderid) {
        List<OrderBooks> items= ordersService.getOrderBooksByOrderId(orderid);
        return Result.suc(items);
    }
}
