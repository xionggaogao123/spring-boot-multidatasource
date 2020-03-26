package com.qy.util;

import com.qy.po.OracleMember;
import com.qy.po.OracleUser;
import com.qy.service.OracleMemberService;
import com.qy.service.OracleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OracleWriteUtil {


    @Autowired
    private OracleMemberService oracleMemberService;

    @Autowired
    private OracleUserService oracleUserService;


    @Async
    public void syncWriteMember(List<OracleMember> members) {
        oracleMemberService.batchSave(members);
    }


    @Async
    public void syncWriteUser(List<OracleUser> oracleUsers) {
        oracleUserService.batchSave(oracleUsers);
    }
}
