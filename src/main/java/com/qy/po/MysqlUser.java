package com.qy.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MysqlUser {

    //主键
    private Long id;
    //用户昵称
    private String nickName;
    //用户真实姓名
    private String realName;
    //登录密码
    private String password;
    //联系电话
    private String phone;
    //国家代码
    private String stateCode;
    //手机号
    private String mobile;
    //头像
    private String pic;
    //性别 0 男 1 女
    private Integer sex;
    //用户登录名
    private String username;
    //邮箱
    private String email;
    //用户类型 0 免费 1试用 2正式
    private Integer type;
    //是否主账号
    private Integer isMain;
    //可创建账号个数 0，暂时最多10个
    private Integer accountNo;
    //主用户ID
    private Long mainUserId;
    //最后更新人id
    private Long operatorId;
    //最后更新人姓名
    private String operatorName;
    //是否禁用
    private Integer disabled;
    //最近登录时间
    private Long loginTime;
    //创建时间
    private Long createTime;
    //最后更新时间
    private Long lastAccess;
    //版本号
    private Long version;

    // 公共资源优化冗余字段 四个member_id,reg_type,reg_source,reg_time字段 TuJun
    private Long memberId;
    private Integer regType;
    private Integer regSource;
    private Long regTime;

    // 最后通话时间 zhangxin 2019年8月21日14:58:01
    private Long lastCallTime;
    // 是否联系人
    private Integer contact;
}
