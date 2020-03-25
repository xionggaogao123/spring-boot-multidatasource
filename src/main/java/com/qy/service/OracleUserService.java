package com.qy.service;

import com.qy.po.OracleUser;

import java.util.List;

public interface OracleUserService {


    OracleUser findById(Long id);

    void batchSave(List<OracleUser> users);

}
