package com.qy.controller;


import com.google.common.collect.Iterables;
import com.qy.controller.task.SyncMemberTask;
import com.qy.po.MysqlMember;
import com.qy.service.MysqlMemberService;
import com.qy.service.OracleMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SyncMemberController {

    private Logger logger = LoggerFactory.getLogger(SyncMemberController.class);

    @Autowired
    private MysqlMemberService mysqlMemberService;

    @Autowired
    private OracleMemberService oracleMemberService;

    private static final Integer SIZE = 30000;

    private static final Integer PRE_SIZE = 1000;

    private static ExecutorService memberExcutor = Executors.newFixedThreadPool(10);

    @RequestMapping("sync-member")
    public void syncMemberData() {
        //分批次查询 mysql 中的 member 中信息, 一个线程读，多个线程写
        Long minId = mysqlMemberService.minId();
        long startTime = System.currentTimeMillis();
        while (true) {
            List<MysqlMember> mysqlMembers = mysqlMemberService.batchQueryWith1w(minId, SIZE);
            if (CollectionUtils.isEmpty(mysqlMembers)) {
                break;
            }
            CountDownLatch latch = new CountDownLatch(getCountDownLatchSize(mysqlMembers.size()));
            subMemberData(mysqlMembers, latch, oracleMemberService);
            try {
                latch.await();
            } catch (InterruptedException e) {
                break;
            }
            minId = Iterables.getLast(mysqlMembers).getId();
            if (mysqlMembers.size() < SIZE) {
                break;
            }
        }
        logger.info("syncMemberData cost time={}", (System.currentTimeMillis() - startTime));
    }


    private Integer getCountDownLatchSize(Integer size) {
        return (size - 1) / PRE_SIZE + 1;
    }

    /**
     * 一次1000
     */
    private void subMemberData(List<MysqlMember> mysqlMembers, CountDownLatch latch, OracleMemberService oracleMemberService) {
        for (int i = 0; i < mysqlMembers.size(); ) {
            int j = i + PRE_SIZE;
            List<MysqlMember> members;
            if (j >= mysqlMembers.size()) {
                members = mysqlMembers.subList(i, mysqlMembers.size());
            } else {
                members = mysqlMembers.subList(i, j);
            }
            i = j;
            memberExcutor.submit(new SyncMemberTask(members, latch, oracleMemberService));
        }
    }
}



