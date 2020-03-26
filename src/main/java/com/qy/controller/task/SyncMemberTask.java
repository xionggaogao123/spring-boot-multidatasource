package com.qy.controller.task;

import com.google.common.base.Throwables;
import com.qy.po.OracleMember;
import com.qy.service.OracleMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class SyncMemberTask implements Callable<Integer> {

    private Logger logger = LoggerFactory.getLogger(SyncMemberTask.class);

    private List<OracleMember> oracleMembers;

    private CountDownLatch latch;

    private OracleMemberService oracleMemberService;

    public SyncMemberTask( List<OracleMember> oracleMembers, CountDownLatch latch,OracleMemberService oracleMemberService) {
        this.oracleMembers = oracleMembers;
        this.latch = latch;
        this.oracleMemberService = oracleMemberService;
    }

    @Override
    public Integer call() throws Exception {
        try {
            oracleMemberService.batchSave(oracleMembers);
        }catch (Exception e) {
            logger.error("SyncMemberTask batchSave error cause={}", Throwables.getStackTraceAsString(e));
            List<Long> ids = oracleMembers.stream().map(OracleMember::getOldSystemId).collect(Collectors.toList());
            logger.error("SyncMemberTask batchSave error with oldSystemIds={}", ids);
        }
        latch.countDown();
        return null;
    }
}
