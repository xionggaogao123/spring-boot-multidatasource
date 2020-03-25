package com.qy.po;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
public class MysqlMember implements Serializable {


    //会员ID 主键
    private Long id;
    //会员中文名称
    private String memberNameCn;
    //会员英文名称
    private String memberNameEn;
    //会员中文简称
    private String shortNameCn;
    //会员英文简称
    private String shortNameEn;
    //公司邮政编码
    private String postcode;
    //国家代码
    private String stateCode;
    //公司电话
    private String phone;
    //公司传真
    private String fax;
    //公司邮箱
    private String email;
    //公司网址
    private String website;
    //是否禁用 0 否 1 禁用
    private Integer disabled;
    //企业类型 字典表/枚举company_type
    private Integer companyType;
    //企业性质 字典表/枚举
    private Integer companyNature;
    //创建人id
    private Long creatorId;
    //创建人姓名
    private String creatorName;
    //创建时间
    private Long createTime;
    //最后更新人id
    private Long operatorId;
    //最后更新人姓名
    private String operatorName;
    //会员所在区域id 区域表
    private Long areaId;
    //会员所在区域编码
    private String areaCode;
    //会员所在区域名称
    private String areaName;
    //公司地址,完整地址
    private String address;
    //最后更新时间
    private Long lastAccess;
    //版本号
    private Long version;

    //以下字段用于公司合并列表展示
    private Long deptId;//业务员部门id
    private Long userId;//用户id
    private String userName;//用户名称
    private Integer userType;//用户类型
    private Long adminId;//业务员id
    private Long matchNum;//匹配公司数量
    private Long targetId;//其中一个匹配公司id
    private Double similarity;//相似度


}
