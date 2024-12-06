package com.wms.service;

import com.wms.entity.Shoppingcart;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
public interface ShoppingcartService extends IService<Shoppingcart> {

    List<Shoppingcart> getCartByUserId(Integer userid);

    boolean addBooksToUserCart(Integer userid, Integer bookid);
    boolean removeItemFromCart(Integer userid, Integer bookid);

    boolean dumpUserCart(Integer userid);

    Integer uniqueItemInCart(Integer userid, Integer bookid);

    boolean newBooksToUserCart(Integer userid, Integer bookid);

    boolean subBooksFromUser(Integer userid, Integer bookid);
}
