package com.qy.service.impl;


import com.qy.mapper.OracleMemberMapper;
import com.qy.po.OracleMember;
import com.qy.service.OracleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OracleMemberServiceImpl implements OracleMemberService {


    @Autowired
    private OracleMemberMapper oracleMemberMapper;

    @Override
    public OracleMember findById(Long id) {
        return oracleMemberMapper.findById(id);
    }

    @Override
    public void batchSave(List<OracleMember> members) {
        oracleMemberMapper.batchSave(members);
    }
}
