package com.qy.mapper;

import com.qy.datasource.TargetDataSource;
import com.qy.po.OracleUser;

import java.util.List;

public interface OracleUserMapper {

    @TargetDataSource("ds2")
    OracleUser findById(Long id);

    @TargetDataSource("ds2")
    void batchSave(List<OracleUser> users);
}
