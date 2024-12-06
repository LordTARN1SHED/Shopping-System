package com.wms.service.impl;

import com.wms.entity.Xuser;
import com.wms.mapper.XuserMapper;
import com.wms.service.XuserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@Service
public class XuserServiceImpl extends ServiceImpl<XuserMapper, Xuser> implements XuserService {
    @Resource
    private XuserMapper xuserMapper;


    @Override
    public void update(Xuser xuser) {
        xuserMapper.update(xuser);
    }
}
