package com.qy.controller;


import com.google.common.collect.Iterables;
import com.qy.controller.task.SyncMemberTask;
import com.qy.controller.task.SyncUserTask;
import com.qy.po.MysqlMember;
import com.qy.po.MysqlUser;
import com.qy.po.OracleMember;
import com.qy.po.OracleUser;
import com.qy.service.MysqlUserService;
import com.qy.service.OracleMemberService;
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
            subMemberData(mysqlUsers, latch, oracleUserService);
            try {
                latch.await();
            } catch (InterruptedException e) {
                break;
            }
            List<OracleUser> oracleUsers = convertMysqlUserToOracle(mysqlUsers);

            oracleWriteUtil.syncWriteUser(oracleUsers);
            minId = Iterables.getLast(mysqlUsers).getId();
            if (mysqlUsers.size() < SIZE) {
                break;
            }
        }
        System.out.println("cost time ====" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 一次6000
     */
    private void subMemberData(List<MysqlUser> mysqlMembers, CountDownLatch latch, OracleUserService oracleMemberService) {
        for (int i = 0; i < mysqlMembers.size(); ) {
            int j = i + PRE_SIZE;
            List<MysqlUser> members;
            if (j >= mysqlMembers.size()) {
                members = mysqlMembers.subList(i, mysqlMembers.size());
            } else {
                members = mysqlMembers.subList(i, j);
            }
            i = j;
            List<OracleUser> oracleMembers = convertMysqlUserToOracle(members);
            userExcutor.submit(new SyncUserTask(oracleMembers, latch,oracleUserService ));
        }
    }


    private Integer getCountDownLatchSize(Integer size) {
        return (size - 1) / PRE_SIZE + 1;
    }

    private List<OracleUser> convertMysqlUserToOracle(List<MysqlUser> mysqlUsers) {
        List<OracleUser> oracleUsers = new ArrayList<>();
        for (MysqlUser userBaseDTO : mysqlUsers) {
            OracleUser user = new OracleUser();
            user.setUsername(userBaseDTO.getUsername());    // 用户名
            user.setPassword(userBaseDTO.getPassword());    // 密码
            user.setName(userBaseDTO.getRealName());      // 名字
            user.setSex(String.valueOf(userBaseDTO.getSex()));      // 性别
            user.setPhone(userBaseDTO.getPhone());      // 电话
            user.setMobile(userBaseDTO.getMobile());      // 手机
            user.setEmail(userBaseDTO.getEmail());      // 邮件
            user.setCountry(userBaseDTO.getStateCode());      // 省 自治区
            user.setState(userBaseDTO.getStateCode());
            user.setMemberId(userBaseDTO.getMemberId());    // 会员代码
            user.setMainUser(userBaseDTO.getIsMain() == null ? null : (userBaseDTO.getIsMain() == 1));    // 是否主用户
            user.setDisabled(userBaseDTO.getDisabled() == null ? null : (userBaseDTO.getDisabled() == 1));    // 是否禁用
            user.setRegisterTime(userBaseDTO.getRegTime());  // 注册时间
            user.setLastAccess(userBaseDTO.getLastAccess());    // 最后更新时间

            user.setOldSystemId(userBaseDTO.getId());
            user.setBusinessType(Byte.valueOf("2"));
            user.setPic(userBaseDTO.getPic());
            user.setType(userBaseDTO.getType() == null ? null : Byte.valueOf(String.valueOf(userBaseDTO.getType())));
            user.setAccountNo(Long.valueOf(userBaseDTO.getAccountNo()));
            user.setMainUserId(userBaseDTO.getMainUserId());
            user.setLoginTime(userBaseDTO.getLoginTime());
            user.setOperatorId(userBaseDTO.getOperatorId());
            user.setOperatorName(userBaseDTO.getOperatorName());
            user.setCreateTime(userBaseDTO.getCreateTime());
            user.setRegType(userBaseDTO.getRegType() == null ? null : Byte.valueOf(String.valueOf(userBaseDTO.getRegType())));
            user.setRegSource(userBaseDTO.getRegSource() == null ? null : Byte.valueOf(String.valueOf(userBaseDTO.getRegSource())));
            user.setVersion(userBaseDTO.getVersion());
            // 隆众无这些属性
            user.setTitle(null);      // 职务
            user.setFax(null);      // 传真
            user.setQq(null);        // qq-新
            user.setWeixin(null);      // 微信-新
            user.setAdminWeixin(null);  // 微信服务号-新
            user.setCity(null);      // 城市
            user.setArea(null);      // 区县
            user.setAddress(null);    // 地址
            user.setPostcode(null);    // 邮编
            user.setLastCallTime(-1L);
            user.setContact((byte) -1);
            user.setFirstSendMsgToImportUser(-1);  //0非钢银导入用户发送短信 ，1 第一次给钢银导入用户发短信，2已经给钢银导入用户发送过短信
            user.setRights(null);      // 权限
            oracleUsers.add(user);
        }
        return oracleUsers;
    }


}
