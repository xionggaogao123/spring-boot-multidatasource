package com.qy.controller;


import com.google.common.collect.Iterables;
import com.qy.controller.task.SyncUserTask;
import com.qy.po.MysqlUser;
import com.qy.service.MysqlUserService;
import com.qy.service.OracleUserService;
import com.qy.util.OracleWriteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SyncUserController {

    @Autowired
    private MysqlUserService mysqlUserService;

    @Autowired
    private OracleUserService oracleUserService;

    @Autowired
    private OracleWriteUtil oracleWriteUtil;

    private static final Integer SIZE = 30000;

    private static final Integer PRE_SIZE = 1000;

    private static ExecutorService userExcutor = Executors.newFixedThreadPool(5);

    @RequestMapping("sync-user")
    public void syncUserData() {
        Long minId = mysqlUserService.minId();
        long startTime = System.currentTimeMillis();
        while (true) {
            List<MysqlUser> mysqlUsers = mysqlUserService.batchQueryUser(minId,SIZE);
            if (CollectionUtils.isEmpty(mysqlUsers)) {
                break;
            }
            CountDownLatch latch = new CountDownLatch(getCountDownLatchSize(mysqlUsers.size()));
            subUserData(mysqlUsers, latch, oracleUserService);
            try {
                latch.await();
            } catch (InterruptedException e) {
                break;
            }
            minId = Iterables.getLast(mysqlUsers).getId();
            if (mysqlUsers.size() < SIZE) {
                break;
            }
        }
        System.out.println("syncUserData cost time ====" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 一次6000
     */
    private void subUserData(List<MysqlUser> mysqlUsers, CountDownLatch latch, OracleUserService oracleMemberService) {
        for (int i = 0; i < mysqlUsers.size(); ) {
            int j = i + PRE_SIZE;
            List<MysqlUser> members;
            if (j >= mysqlUsers.size()) {
                members = mysqlUsers.subList(i, mysqlUsers.size());
            } else {
                members = mysqlUsers.subList(i, j);
            }
            i = j;
            userExcutor.submit(new SyncUserTask(members, latch,oracleUserService ));
        }
    }


    private Integer getCountDownLatchSize(Integer size) {
        return (size - 1) / PRE_SIZE + 1;
    }



}
