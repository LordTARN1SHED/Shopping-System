package com.wms.mapper;

import com.wms.entity.Invitations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jrd
 * @since 2023-12-21
 */
@Mapper
public interface InvitationsMapper extends BaseMapper<Invitations> {
    @Select("SELECT * FROM invitations WHERE invitation = #{invitationCode}")
    Invitations findByInvitation(String invitationCode);
}
