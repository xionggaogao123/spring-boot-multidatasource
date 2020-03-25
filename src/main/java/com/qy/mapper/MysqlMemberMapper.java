package com.qy.mapper;

import com.qy.datasource.TargetDataSource;
import com.qy.po.MysqlMember;

import java.util.List;
import java.util.Map;

public interface MysqlMemberMapper {


    @TargetDataSource("ds1")
    MysqlMember findById(Long id);

    @TargetDataSource("ds1")
    Long minId();

    @TargetDataSource("ds1")
    List<MysqlMember> batchQueryWith1w(Map<String,Object> param);

}
