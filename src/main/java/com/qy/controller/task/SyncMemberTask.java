package com.qy.controller.task;

import com.google.common.base.Throwables;
import com.qy.po.MysqlMember;
import com.qy.po.OracleMember;
import com.qy.service.OracleMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class SyncMemberTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SyncMemberTask.class);

    private List<MysqlMember> mysqlMembers;

    private CountDownLatch latch;

    private OracleMemberService oracleMemberService;

    public SyncMemberTask( List<MysqlMember> mysqlMembers, CountDownLatch latch,OracleMemberService oracleMemberService) {
        this.mysqlMembers = mysqlMembers;
        this.latch = latch;
        this.oracleMemberService = oracleMemberService;
    }

    @Override
    public void run() {
        try {
            List<OracleMember> oracleMembers = convertMysqlMemberToOracle(mysqlMembers);
            oracleMemberService.batchSave(oracleMembers);
        }catch (Exception e) {
            logger.error("SyncMemberTask batchSave error cause={}", Throwables.getStackTraceAsString(e));
        }
        latch.countDown();
    }

    private List<OracleMember> convertMysqlMemberToOracle(List<MysqlMember> mysqlMembers) {
        List<OracleMember> oracleMembers = new ArrayList<>();
        for (MysqlMember memberBaseDTO : mysqlMembers) {
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
            member.setDisabled(memberBaseDTO.getDisabled() != 0);
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
            member.setBusinessType((byte) 2);
            oracleMembers.add(member);
        }
        return oracleMembers;
    }
}
