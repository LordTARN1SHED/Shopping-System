package com.wms.controller;


import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Invitations;
import com.wms.service.InvitationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jrd
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/inv")
public class InvitationsController {
    @Autowired
    private InvitationsService invitationsService;

    @RequestMapping("/register")
    public Result register(@RequestBody Map<String, Object> obj){
        String invitationCode = (String) obj.get("invitation");
        Invitations invitations = invitationsService.findByInvitation(invitationCode);
        if (invitations!=null) return Result.suc(invitations);
        else return Result.fail();
    }
}
