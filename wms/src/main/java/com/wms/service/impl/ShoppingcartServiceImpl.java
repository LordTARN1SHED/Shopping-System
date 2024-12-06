package com.wms.service.impl;

import com.wms.entity.Shoppingcart;
import com.wms.mapper.ShoppingcartMapper;
import com.wms.service.ShoppingcartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@Service
public class ShoppingcartServiceImpl extends ServiceImpl<ShoppingcartMapper, Shoppingcart> implements ShoppingcartService {
    @Autowired
    private ShoppingcartMapper shoppingcartMapper;

    @Override
    public Integer uniqueItemInCart(Integer userid, Integer bookid) {
        return shoppingcartMapper.uniqueItemInCart(userid,bookid);
    }

    @Override
    public boolean newBooksToUserCart(Integer userid, Integer bookid) {
        return shoppingcartMapper.newBooksToUserCart(userid,bookid);
    }

    @Override
    public List<Shoppingcart> getCartByUserId(Integer userid) {
        return shoppingcartMapper.getCartByUserId(userid);
    }

    @Override
    public boolean addBooksToUserCart(Integer userid, Integer bookid) {
        return shoppingcartMapper.addBooksToUserCart(userid,bookid)>0;
    }

    @Override
    public boolean subBooksFromUser(Integer userid, Integer bookid) {
        return shoppingcartMapper.subBooksFromUser(userid,bookid);
    }

    @Override
    public boolean removeItemFromCart(Integer userid, Integer bookid) {
        return shoppingcartMapper.removeItemFromCart(userid, bookid) > 0;
    }

    @Override
    public boolean dumpUserCart(Integer userid) {
        return shoppingcartMapper.dumpUserCart(userid)>0;
    }

}
