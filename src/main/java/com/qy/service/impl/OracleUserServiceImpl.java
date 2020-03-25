package com.qy.service.impl;


import com.qy.mapper.OracleUserMapper;
import com.qy.po.OracleUser;
import com.qy.service.OracleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OracleUserServiceImpl implements OracleUserService {

    @Autowired
    private OracleUserMapper oracleUserMapper;

    @Override
    public OracleUser findById(Long id) {
        return oracleUserMapper.findById(id);
    }


    @Override
    public void batchSave(List<OracleUser> users) {
        oracleUserMapper.batchSave(users);
    }
}
