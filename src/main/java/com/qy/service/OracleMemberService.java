package com.qy.service;

import com.qy.po.OracleMember;

import java.util.List;

public interface OracleMemberService {


    OracleMember findById(Long id);


    void batchSave(List<OracleMember> members);




}
