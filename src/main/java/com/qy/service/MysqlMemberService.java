package com.qy.service;

import com.qy.po.MysqlMember;

import java.util.List;

public interface MysqlMemberService {


    MysqlMember findById(Long id);


    Long minId();

    /**
     * 批量查询，一次查1w
     * @param id
     * @param size
     * @return
     */
    List<MysqlMember> batchQueryWith1w(Long id,Integer size);




}
