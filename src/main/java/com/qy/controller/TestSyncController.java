package com.qy.controller;


import com.google.common.collect.Iterables;
import com.qy.po.MysqlMember;
import com.qy.po.MysqlUser;
import com.qy.po.OracleMember;
import com.qy.po.OracleUser;
import com.qy.service.MysqlMemberService;
import com.qy.service.MysqlUserService;
import com.qy.service.OracleMemberService;
import com.qy.service.OracleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestSyncController {

    @Autowired
    private OracleMemberService oracleMemberService;

    @Autowired
    private MysqlMemberService mysqlMemberService;

    @Autowired
    private OracleUserService oracleUserService;

    @Autowired
    private MysqlUserService mysqlUserService;


    private final static Integer SIZE = 1000;


    @RequestMapping("test-find-id")
    public OracleMember findById(Long id) {
        OracleMember member =  oracleMemberService.findById(id);
        return member;
    }

    @RequestMapping("mysql-findById")
    public MysqlMember findByIdOfMsql(Long id) {
        MysqlMember mysqlMember = mysqlMemberService.findById(id);
        return mysqlMember;
    }

    @RequestMapping("findOracleUserById")
    public OracleUser findOracleUserById(Long id) {
        return oracleUserService.findById(id);
    }

    @RequestMapping("findMysqlUserById")
    public MysqlUser findMysqlUserById(Long id) {
        return mysqlUserService.findById(id);
    }


    /**
     * mysql member 批量查询
     */
    @RequestMapping("mysql-batchQuery")
    public void testBatchQuery() {
        Long minId = mysqlMemberService.minId();
        long startTime = System.currentTimeMillis();
        while (true) {
            List<MysqlMember> mysqlMembers = mysqlMemberService.batchQueryWith1w(minId,SIZE);
            minId = Iterables.getLast(mysqlMembers).getId();
            System.out.println("======== id =======" + minId);
            if (mysqlMembers.size() < SIZE) {
                break;
            }
        }
        System.out.println("cost time ====" + (System.currentTimeMillis() - startTime));
    }


    /**
     * mysql user 批量查询
     */
    @RequestMapping
    public void testBatchQueryUser() {
        Long minId = mysqlUserService.minId();
        long startTime = System.currentTimeMillis();
        while (true) {
            List<MysqlUser> mysqlUsers = mysqlUserService.batchQueryUser(minId,SIZE);
            minId = Iterables.getLast(mysqlUsers).getId();
            System.out.println("======== id =======" + minId);
            if (mysqlUsers.size() < SIZE) {
                break;
            }
        }
        System.out.println("cost time ====" + (System.currentTimeMillis() - startTime));
    }


    /**
     * 测试 member 批量插入
     */
    @RequestMapping("batch-saveMember")
    public void testBatchSaveMember() {
        Long minId = 1L;
        List<MysqlMember> mysqlUsers = mysqlMemberService.batchQueryWith1w(minId,2);
        List<OracleMember> oracleMembers = convertMysqlMemberToOracle(mysqlUsers);
        oracleMemberService.batchSave(oracleMembers);

    }

    private List<OracleMember> convertMysqlMemberToOracle(List<MysqlMember> mysqlMembers){
        List<OracleMember> oracleMembers = new ArrayList<>();
        for (MysqlMember memberBaseDTO :mysqlMembers) {
            OracleMember member = new OracleMember();
            // id不可使用隆众传递来的id,走序列
            //member.setId(memberBaseDTO.getId());
            member.setName(memberBaseDTO.getMemberNameCn());
            // TODO 会员英文名称
            member.setShortName(memberBaseDTO.getShortNameCn());
            // TODO 会员英文简称
            //企业类型 字典表/枚举
            member.setMemberType(String.valueOf(memberBaseDTO.getCompanyType()));
            member.setCompanyNature(memberBaseDTO.getCompanyNature().byteValue());
            member.setAreaId(memberBaseDTO.getAreaId());
            member.setAreaCode(memberBaseDTO.getAreaCode());
            member.setAreaName(memberBaseDTO.getAreaName());
            member.setAddress(memberBaseDTO.getAddress());
            member.setPostcode(memberBaseDTO.getPostcode());
            //国家代码(钢联：国家名称；隆众：国家代码，比如CN)
            member.setState(memberBaseDTO.getStateCode());
            member.setPhone(memberBaseDTO.getPhone());
            member.setFax(memberBaseDTO.getFax());
            member.setEmail(memberBaseDTO.getEmail());
            member.setWebsite(memberBaseDTO.getWebsite());
            member.setDisabled(memberBaseDTO.getDisabled() == 0 ? true : false);
            member.setCreatorId(memberBaseDTO.getCreatorId());
            member.setCreatorName(memberBaseDTO.getCreatorName());
            member.setCreatorTime(memberBaseDTO.getCreateTime());
            member.setOperatorId(memberBaseDTO.getOperatorId());
            member.setOperatorName(memberBaseDTO.getOperatorName());
            member.setLastAccess(memberBaseDTO.getLastAccess());
            member.setVersion(memberBaseDTO.getVersion());
            // 老系统id
            member.setOldSystemId(memberBaseDTO.getId());
            // 默认隆众数据
            member.setBusinessType((byte)2);
            oracleMembers.add(member);
        }
        return oracleMembers;
    }


    /**
     * 测试 user 批量插入
     */
    @RequestMapping("batch-saveUser")
    public void testBatchSaveUser() {
        Long minId = 1000L;
        List<MysqlUser> mysqlUsers = mysqlUserService.batchQueryUser(minId,2);
        List<OracleUser> oracleUsers = convertMysqlUserToOracle(mysqlUsers);

        try {
            

        }catch (Exception e) {
            e.getMessage();
        }
        oracleUserService.batchSave(oracleUsers);
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
