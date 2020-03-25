package com.qy.service;

import com.qy.po.MysqlUser;

import java.util.List;

public interface MysqlUserService {


    MysqlUser findById(Long id);

    Long minId();

    List<MysqlUser> batchQueryUser(Long id, Integer size);

}
