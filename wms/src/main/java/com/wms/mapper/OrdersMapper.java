package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.OrderBooks;
import com.wms.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Laplace
 * @since 2024-9-14
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    @Select("SELECT * FROM orders WHERE userid = #{userid} ORDER BY orderid asc")
    List<Orders> getOrdersByUserId(Integer userid);

    @Select("SELECT * FROM orderbook where orderid = #{orderid}")
    List<OrderBooks> getOrderBooksByOrderId(Integer orderid);

    @Options(useGeneratedKeys = true, keyColumn = "orderid")
    int insertSelective(Orders order);

    @Insert("INSERT INTO orderbook (orderid, bookid, num) VALUES (#{orderid}, #{bookid}, #{num})")
    int insertOrderBook(Integer orderid, Integer bookid, Integer num);

    @Update("UPDATE orders SET payornot = #{payornot} WHERE orderid = #{orderid}")
    int updateByOrderid(Integer orderid, Integer payornot);
}
