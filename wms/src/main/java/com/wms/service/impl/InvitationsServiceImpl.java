package com.wms.service.impl;

import com.wms.entity.Invitations;
import com.wms.mapper.InvitationsMapper;
import com.wms.service.InvitationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jrd
 * @since 2023-12-21
 */
@Service
public class InvitationsServiceImpl extends ServiceImpl<InvitationsMapper, Invitations> implements InvitationsService {

    @Autowired
    private InvitationsMapper invitationsMapper;
    @Override
    public Invitations findByInvitation(String invitationCode) {
        return invitationsMapper.findByInvitation(invitationCode);
    }

}
