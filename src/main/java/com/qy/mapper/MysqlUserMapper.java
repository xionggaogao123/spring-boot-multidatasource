package com.qy.mapper;

import com.qy.datasource.TargetDataSource;
import com.qy.po.MysqlUser;

import java.util.List;
import java.util.Map;

public interface MysqlUserMapper {

    @TargetDataSource("ds1")
    MysqlUser findById(Long id);

    @TargetDataSource("ds1")
    Long minId();

    @TargetDataSource("ds1")
    List<MysqlUser> batchQueryUser(Map<String, Object> param);
}
