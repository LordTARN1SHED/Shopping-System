package com.wms.service;

import com.wms.entity.Invitations;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jrd
 * @since 2023-12-21
 */
public interface InvitationsService extends IService<Invitations> {
    Invitations findByInvitation(String invitationCode);
}
