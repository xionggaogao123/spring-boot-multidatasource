package com.qy.controller.task;

import com.google.common.base.Throwables;
import com.qy.po.OracleUser;
import com.qy.service.OracleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class SyncUserTask implements Runnable{

    private Logger logger = LoggerFactory.getLogger(SyncUserTask.class);

    private List<OracleUser> oracleUsers;

    private CountDownLatch latch;

    private OracleUserService oracleUserService;


    public SyncUserTask( List<OracleUser> oracleUsers,CountDownLatch countDownLatch,OracleUserService oracleUserService) {
        this.oracleUsers = oracleUsers;
        this.latch = countDownLatch;
        this.oracleUserService = oracleUserService;
    }

    @Override
    public void run() {
        try {
            oracleUserService.batchSave(oracleUsers);
        }catch (Exception e) {
            logger.error("SyncUserTask batchSave error cause={}", Throwables.getStackTraceAsString(e));
            List<Long> ids = oracleUsers.stream().map(OracleUser::getOldSystemId).collect(Collectors.toList());
            logger.error("SyncUserTask batchSave error with oldSystemIds={}", ids);
        }
        latch.countDown();

    }
}
