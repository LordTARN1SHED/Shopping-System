package com.wms.mapper;

import com.wms.entity.Xuser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@Mapper
public interface XuserMapper extends BaseMapper<Xuser> {

    @Update("update xuser set balance=#{balance} where id=#{id}")
    void update(Xuser xuser);
}
