package com.qy.service.impl;


import com.qy.mapper.MysqlUserMapper;
import com.qy.po.MysqlUser;
import com.qy.service.MysqlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MysqlUserServiceImpl implements MysqlUserService {


    @Autowired
    private MysqlUserMapper mysqlUserMapper;

    @Override
    public MysqlUser findById(Long id) {
        return mysqlUserMapper.findById(id);
    }


    @Override
    public Long minId() {
        return mysqlUserMapper.minId();
    }

    @Override
    public List<MysqlUser> batchQueryUser(Long id, Integer size) {
        Map<String, Object> param = new HashMap<>();
        param.put("lastId", id);
        param.put("limit", size);
        return mysqlUserMapper.batchQueryUser(param);
    }
}
