package com.wms.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.wms.common.QueryObj;
import com.wms.common.Result;
import com.wms.entity.Books;
import com.wms.entity.Shoppingcart;
import com.wms.service.ShoppingcartService;
import io.swagger.models.auth.In;
import jdk.nashorn.internal.runtime.SharedPropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@RestController
@RequestMapping("/cart")
public class ShoppingcartController {
    @Autowired
    private ShoppingcartService shoppingcartService;

    @RequestMapping("/list/{userid}")
    public Result getCartByUserId(@PathVariable Integer userid) {
        List<Shoppingcart> items = shoppingcartService.getCartByUserId(userid);
        return Result.suc(items);
    }

    @RequestMapping("/new/{userid}/{bookid}")
    public Result newBooksToUserCart(@PathVariable Integer userid, @PathVariable Integer bookid){
        return shoppingcartService.newBooksToUserCart(userid,bookid)?
                Result.suc(): Result.fail();
    }
    @RequestMapping("/add/{userid}/{bookid}")
    public Result addBooksToUserCart(@PathVariable Integer userid, @PathVariable Integer bookid){
        return shoppingcartService.addBooksToUserCart(userid,bookid)?
                Result.suc(): Result.fail();
    }

    @PostMapping("/sub/{userid}/{bookid}")
    public Result subBooksFromUserCart(@PathVariable Integer userid, @PathVariable Integer bookid){
        return shoppingcartService.subBooksFromUser(userid,bookid)?
                Result.suc(): Result.fail();
    }

    @RequestMapping("/del/{userid}/{bookid}")
    public Result removeItemFromCart(@PathVariable Integer userid, @PathVariable Integer bookid) {
        return shoppingcartService.removeItemFromCart(userid, bookid) ? Result.suc() : Result.fail();
    }
    @RequestMapping("/dump/{userid}")
    public Result dumpUserCart(@PathVariable Integer userid){
        return shoppingcartService.dumpUserCart(userid)?Result.suc():Result.fail();
    }

    @RequestMapping("/unique")
    public Result uniqueItemInCart(@RequestBody QueryObj queryObj){
        int res=-1;
        res=shoppingcartService.uniqueItemInCart(queryObj.userid,queryObj.bookid);
        if (res==-1){
            return Result.fail();
        }
        else {
            return Result.suc(res);
        }
    }
    @PostMapping("/add")
    public Result addItemToCart(@RequestBody Shoppingcart item) {
        return shoppingcartService.save(item) ? Result.suc() : Result.fail();
    }

    @PostMapping("/update")
    public Result updateItemInCart(@RequestBody Shoppingcart item){
        return shoppingcartService.updateById(item) ? Result.suc() : Result.fail();
    }



}
