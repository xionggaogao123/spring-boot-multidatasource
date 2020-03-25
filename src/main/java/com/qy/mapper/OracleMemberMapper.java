package com.qy.mapper;

import com.qy.datasource.TargetDataSource;
import com.qy.po.OracleMember;

import java.util.List;

public interface OracleMemberMapper {

    @TargetDataSource("ds2")
    OracleMember findById(Long id);

    @TargetDataSource("ds2")
    void batchSave(List<OracleMember> members);

}
