package com.wms.mapper;

import com.wms.entity.Shoppingcart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@Mapper
public interface ShoppingcartMapper extends BaseMapper<Shoppingcart> {
//    @Select("SELECT * FROM shoppingcart WHERE userid = #{userid}")
    List<Shoppingcart> getCartByUserId(Integer userid);

    @Select("SELECT COUNT(*) FROM shoppingcart WHERE userid = #{userid} AND bookid = #{bookid}")
    int uniqueItemInCart(Integer userid, Integer bookid);

    @Update("UPDATE shoppingcart SET number = number+1 WHERE userid = #{userid} AND bookid = #{bookid}")
    int addBooksToUserCart(Integer userid, Integer bookid);

    @Delete("DELETE FROM shoppingcart WHERE userid = #{userid} AND bookid = #{bookid}")
    int removeItemFromCart(Integer userid, Integer bookid);

    @Delete("DELETE FROM shoppingcart WHERE userid = #{userid}")
    int dumpUserCart(Integer userid);

    @Insert("INSERT INTO shoppingcart (userid, bookid, number) VALUES (#{userid}, #{bookid}, 1)")
    boolean newBooksToUserCart(Integer userid, Integer bookid);

    @Update("UPDATE shoppingcart SET number = number-1 WHERE userid = #{userid} AND bookid = #{bookid}")
    boolean subBooksFromUser(Integer userid, Integer bookid);
}
