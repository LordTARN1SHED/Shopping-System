package com.wms.controller;

import com.wms.common.QueryObj;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.Xuser;
import com.wms.service.MenuService;
import com.wms.service.XuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/xuser")
public class XuserController {
    @Autowired
    private XuserService xuserService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/findByName")
    public Result findByName(@RequestBody String name){
        // 这里用lambdaQuery()进行数据库查询，而不是自己写然后映射数据库操作
        List list = xuserService.lambdaQuery().eq(Xuser::getName,name).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    @GetMapping("/list")
    public List<Xuser> list(){
        return xuserService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Xuser xuser){
        return xuserService.save(xuser)?Result.suc():Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Xuser xuser){
        return xuserService.updateById(xuser)?Result.suc():Result.fail();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Xuser xuser){
        // 使用lambda表达式查询列表
        List list = xuserService.lambdaQuery()
                .eq(Xuser::getName,xuser.getName())
                .eq(Xuser::getPassword,xuser.getPassword())
                .list();
        if(list.size()>0){
            Xuser xuser1 = (Xuser) list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,xuser1.getRole()).list();
            HashMap res = new HashMap();
            res.put("user",xuser1);
            res.put("menu",menuList);
            return Result.suc(res);
        }
        return Result.fail();
    }

    @PostMapping("/costBalance")
    public Result updateBalance(@RequestBody QueryObj obj){
        Integer id=obj.id;
        Double cost=obj.cost;
        Xuser xuser=xuserService.getById(id);
        if (xuser.getBalance()>=cost){
            double newBalance=xuser.getBalance()-cost;
            xuser.setBalance(newBalance);
            //减去成功，并保存
            xuserService.update(xuser);
            return Result.suc(xuser);
        }
        else{
            //减去失败
            return Result.fail();
        }
    }

}
