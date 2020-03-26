package com.qy.controller.task;

import com.google.common.base.Throwables;
import com.qy.po.MysqlUser;
import com.qy.po.OracleUser;
import com.qy.service.OracleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class SyncUserTask implements Callable<Integer> {

    private Logger logger = LoggerFactory.getLogger(SyncUserTask.class);

    private List<MysqlUser> mysqlUsers;

    private CountDownLatch latch;

    private OracleUserService oracleUserService;


    public SyncUserTask( List<MysqlUser> mysqlUsers,CountDownLatch countDownLatch,OracleUserService oracleUserService) {
        this.mysqlUsers = mysqlUsers;
        this.latch = countDownLatch;
        this.oracleUserService = oracleUserService;
    }

    @Override
    public Integer call() throws Exception {
        try {
            List<OracleUser> oracleUsers = convertMysqlUserToOracle(mysqlUsers);
            oracleUserService.batchSave(oracleUsers);
        }catch (Exception e) {
            logger.error("SyncUserTask batchSave error cause={}", Throwables.getStackTraceAsString(e));
        }
        latch.countDown();
        return 0;
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
