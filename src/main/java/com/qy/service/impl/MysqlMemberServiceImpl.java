package com.qy.service.impl;

import com.qy.mapper.MysqlMemberMapper;
import com.qy.po.MysqlMember;
import com.qy.service.MysqlMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MysqlMemberServiceImpl implements MysqlMemberService {

    @Autowired
    private MysqlMemberMapper mysqlMemberMapper;

    @Override
    public MysqlMember findById(Long id) {
        return mysqlMemberMapper.findById(id);
    }


    @Override
    public Long minId() {
        return mysqlMemberMapper.minId();
    }

    @Override
    public List<MysqlMember> batchQueryWith1w(Long lastId, Integer size) {
        Map<String, Object> param = new HashMap<>();
        param.put("lastId", lastId);
        param.put("limit",size);
        return mysqlMemberMapper.batchQueryWith1w(param);
    }
}
