package com.qy.po;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OracleUser {

    private Long id;					// 用户代码

    private String username = "";		// 用户名
    private String password = "";		// 密码

    private String name = "";			// 名字
    private String sex = "";			// 性别
    private String title = "";			// 职务
    private String phone = "";			// 电话
    private String mobile = "";			// 手机
    private String fax = "";			// 传真
    private String email = "";			// 邮件
    private String country = "";		// 国家
    private String state = "";			// 省 自治区
    private String city = "";			// 城市
    private String area = "";			// 区县
    private String address = "";		// 地址
    private String postcode = "";		// 邮编
    private String qq = "";				// qq-新
    private String weixin = "";			// 微信-新
    private String adminWeixin = "";	// 微信服务号-新

    private long memberId;				// 会员代码
    private String rights = "";			// 权限
    private boolean mainUser;			// 是否主用户

    private boolean disabled;			// 是否禁用
    private long registerTime;			// 注册时间
    private long lastAccess;			// 最后更新时间
    private int firstSendMsgToImportUser;  //0非钢银导入用户发送短信 ，1 第一次给钢银导入用户发短信，2已经给钢银导入用户发送过短信

    /**
     * 老系统id
     */
    private long oldSystemId;

    /**
     * 业务系统编号：钢联 1,隆众 2
     */
    private byte businessType;

    /**
     * 头像
     */
    private String pic;

    /**
     * 用户类型 0 免费 1试用 2正式
     */
    private byte type;

    /**
     *可创建账号个数 0，暂时最多10个
     */
    private long accountNo;

    /**
     * 主用户ID
     */
    private long mainUserId;

    /**
     * 最近登录时间
     */
    private long loginTime;

    /**
     * 创建时间
     */
    private long createTime;


    /**
     * 最后更新人id
     */
    private long operatorId;

    /**
     * 最后更新人姓名
     */
    private String operatorName;

    /**
     * 册方式 0 自主注册 1 业务员代注册 2 账号添加 3 对外接口注册
     */
    private byte regType;

    /**
     * 注册来源 0-PC资讯 1-移动APP 2-短信 3-会议 4-百度推广 5-其他 6-隆众数据 7-隆众快讯 8-微信推广
     */
    private byte regSource;

    /**
     * 版本号
     */
    private long version;

    /**
     * 最后通话时间
     */
    private long lastCallTime;

    /**
     * 是否联系人
     */
    private byte contact;

}
