package com.wms.service;

import com.wms.entity.Xuser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
public interface XuserService extends IService<Xuser> {

    void update(Xuser xuser);
}
