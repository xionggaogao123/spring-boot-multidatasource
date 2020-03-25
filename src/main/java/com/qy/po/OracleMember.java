package com.qy.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysteel.core.Entity;
import com.mysteel.member.member.MemberCompanyInfo;
import com.mysteel.util.DateUtils;
import com.mysteel.util.ObjectUtils;
import com.mysteel.util.StringUtils;
import com.mysteel.utils.CacheKeyUtil;


/**
 * 会员
 *
 * @author wangli
 *
 * 2011-11-16
 */
public class OracleMember implements Entity
{
	private static final long serialVersionUID = 6319976455284560096L;

	private long id;

	private String name = "";				// 会员名称
	private String shortName = "";			// 会员简称

	private String country = "";			// 国家
	private String state = "";				// 省州/自治区
	private String city = "";				// 城市
	private String area = "";				// 区/县
	private String address = "";			// 街道地址
	private String postcode = "";			// 邮编
	private String phone = "";				// 联系电话
	private String fax = "";				// 传真
	private String email = "";				// 邮箱
	private String website = "";			// 网站
	private int isAdd = 0;					// 是否新增-新
	private int isTao = 0;					// 是否后台淘出-新
	private String benefit  = "";			// 享受的优惠申请-新
	private int isSave = 0;             // 是否保留-新
	private int memberSource = 0;			// 客户来源-新

	private MemberCompanyInfo companyInfo;

	private String memberType = "";			// 会员类型  流通企业/钢铁企业/最终用户/相关行业/政府机构/研究机构/海外企业/驻华机构/个人用户/钢材市场/原料企业/证券行业/期货公司
	private long managerId;					// 管理员代码
	private String catalog = "";			// 自定义分类
	private String source = "";				// 注册来源
	private String ipAddr = "";				// 注册IP地址
	private int registerType;				// 注册方式 0-自行注册 1-管理代注册 2-短信代注册 3-手机注册 4-邀请注册 5-黑色金属代注册
	private long proxyRegistrantId;			// 代理注册人代码
	private long registerTime;				// 注册时间
	private long lastLoginTime;				// 最后登录时间
	private long lastServeTime;				// 最后服务时间
	private long lastTransferTime;			// 最后划转时间
	private String lastTransfer = "";		// 最后划转人
	private int visible;					// 是否可见  0-属于服务组 1-属于销售组 2-属于外办组
	private boolean disabled;				// 是否被禁用
	private long lastAccess;				// 最后更新时间


	/**
	 * 老系统id
	 */
	private long oldSystemId;

	/**
	 * 业务系统编号：钢联 1,隆众 2
	 */
	private byte businessType = 1;

	/**
	 * 企业性质 : 国有企业、合资企业、中外合资企业、外资企业、海外企业、其他
	 */
	private byte companyNature;

	/**
	 *会员所在区域id 区域表
	 */
	private long areaId;

	/**
	 * 会员所在区域编码
	 */
	private String areaCode= "";

	/**
	 * 会员所在区域名称
	 */
	private String areaName= "";

	/**
	 * 创建人id
	 */
	private long creatorId;

	/**
	 * 创建人姓名
	 */
	private String creatorName= "";

	/**
	 * 创建时间
	 */
	private long creatorTime;

	/**
	 * 最后更新人id
	 */
	private long operatorId;

	/**
	 * 最后更新人姓名
	 */
	private String operatorName= "";

	/**
	 * 版本号
	 */
	private long version;

	public OracleMember()
	{
	}

	public OracleMember(String name,
                        String shortName,
                        String country,
                        String state,
                        String city,
                        String area,
                        String address,
                        String postcode,
                        String phone,
                        String fax,
                        String email,
                        String website,
                        MemberCompanyInfo companyInfo,
                        String memberType,
                        String source,
                        String ipAddr,
                        int registerType,
                        long proxyRegistrantId)
	{
		this.id = 0L;
		this.name = name;
		this.shortName = shortName;
		this.country = country;
		this.state = state;
		this.city = city;
		this.area = area;
		this.address = address;
		this.postcode = !StringUtils.isTrimEmpty(postcode) && postcode.length() > 6 ? postcode.substring(0, 6) : postcode;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.website = website;
		this.companyInfo = companyInfo;
		this.memberType = memberType;
		this.managerId = 0L;
		this.catalog = "";
		this.source = source;
		this.ipAddr = ipAddr;
		this.registerType = registerType;
		this.proxyRegistrantId = proxyRegistrantId;
		this.registerTime = System.currentTimeMillis();
		this.lastLoginTime = 0L;
		this.lastServeTime = 0L;
		this.lastTransferTime = 0L;
		this.lastTransfer = "";
		this.visible = 0;
		this.disabled = false;
		this.lastAccess = 0L;
	}

	/**
	 * 获取会员代码
	 *
	 * @return
	 */
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * 获取会员名称
	 *
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getShortName()
	{
		return shortName;
	}
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}

	public String getArea()
	{
		return area;
	}
	public void setArea(String area)
	{
		this.area = area;
	}

	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPostcode()
	{
		return postcode;
	}
	public void setPostcode(String postcode)
	{
		this.postcode = !StringUtils.isTrimEmpty(postcode) && postcode.length() > 6 ? postcode.substring(0, 6) : postcode;
	}

	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getFax()
	{
		return fax;
	}
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getWebsite()
	{
		return website;
	}
	public void setWebsite(String website)
	{
		this.website = website;
	}

	public MemberCompanyInfo getCompanyInfo()
	{
		return companyInfo;
	}
	public void setCompanyInfo(MemberCompanyInfo companyInfo)
	{
		this.companyInfo = companyInfo;
	}

	public String getMemberType()
	{
		return memberType;
	}
	public void setMemberType(String memberType)
	{
		this.memberType = memberType;
	}

	public long getManagerId()
	{
		return managerId;
	}
	public void setManagerId(long managerId)
	{
		this.managerId = managerId;
	}

	public String getCatalog()
	{
		return catalog;
	}
	public void setCatalog(String catalog)
	{
		this.catalog = catalog;
	}

	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}

	public String getIpAddr()
	{
		return ipAddr;
	}
	public void setIpAddr(String ipAddr)
	{
		this.ipAddr = ipAddr;
	}

	public int getRegisterType()
	{
		return registerType;
	}
	public void setRegisterType(int registerType)
	{
		this.registerType = registerType;
	}

	public long getProxyRegistrantId()
	{
		return proxyRegistrantId;
	}
	public void setProxyRegistrantId(long proxyRegistrantId)
	{
		this.proxyRegistrantId = proxyRegistrantId;
	}

	public long getRegisterTime()
	{
		return registerTime;
	}
	public String formatRegisterTime(String style)
	{
		if (ObjectUtils.isNull(this.getRegisterTime()) || registerTime == 0L)
		{
			return StringUtils.EMPTY;
		}

		return DateUtils.formatDate(registerTime, style);
	}
	public void setRegisterTime(long registerTime)
	{
		this.registerTime = registerTime;
	}

	public long getLastLoginTime()
	{
		return lastLoginTime;
	}
	public String formatLastLoginTime(String style)
	{
		if (lastLoginTime == 0L)
		{
			return StringUtils.EMPTY;
		}

		return DateUtils.formatDate(lastLoginTime, style);
	}
	public void setLastLoginTime(long lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	public long getLastServeTime()
	{
		return lastServeTime;
	}
	public String formatLastServeTime(String style)
	{
		if (lastServeTime == 0L)
		{
			return StringUtils.EMPTY;
		}

		return DateUtils.formatDate(lastServeTime, style);
	}
	public void setLastServeTime(long lastServeTime)
	{
		this.lastServeTime = lastServeTime;
	}

	public long getLastTransferTime()
	{
		return lastTransferTime;
	}
	public String formatLastTransferTime(String style)
	{
		if (lastTransferTime == 0L)
		{
			return StringUtils.EMPTY;
		}

		return DateUtils.formatDate(lastTransferTime, style);
	}
	public void setLastTransferTime(long lastTransferTime)
	{
		this.lastTransferTime = lastTransferTime;
	}

	public String getLastTransfer()
	{
		return lastTransfer;
	}
	public void setLastTransfer(String lastTransfer)
	{
		this.lastTransfer = lastTransfer;
	}

	public int getVisible()
	{
		return visible;
	}
	public void setVisible(int visible)
	{
		this.visible = visible;
	}

	public boolean isDisabled()
	{
		return disabled;
	}
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	public long getLastAccess()
	{
		return lastAccess;
	}
	public String formatLastAccess(String style)
	{
		if (lastAccess == 0L)
		{
			return StringUtils.EMPTY;
		}

		return DateUtils.formatDate(lastAccess, style);
	}
	public void setLastAccess(long lastAccess)
	{
		this.lastAccess = lastAccess;
	}

	public int getIsAdd()
	{
		return isAdd;
	}
	public void setIsAdd(int isAdd)
	{
		this.isAdd = isAdd;
	}

	public int getIsTao()
	{
		return isTao;
	}
	public void setIsTao(int isTao)
	{
		this.isTao = isTao;
	}

	public String getBenefit()
	{
		return StringUtils.nullSafe(benefit);
	}
	public void setBenefit(String benefit)
	{
		this.benefit = StringUtils.nullSafe(benefit);
	}

	public int getIsSave()
	{
		return isSave;
	}
	public void setIsSave(int isSave)
	{
		this.isSave = isSave;
	}

	public int getMemberSource()
	{
		return memberSource;
	}
	public void setMemberSource(int memberSource)
	{
		this.memberSource = memberSource;
	}

	/**
	 * 获取会员缓存代码
	 *
	 * @return
	 */
	@JsonIgnore
	public String getCacheId()
	{
		return getCacheId(getId());
	}

	public static String getCacheId(long memberId)
	{
		return CacheKeyUtil.getId(com.mysteel.member.member.Member.class, memberId);
	}

	public long getOldSystemId() {
		return oldSystemId;
	}

	public void setOldSystemId(long oldSystemId) {
		this.oldSystemId = oldSystemId;
	}

	public byte getBusinessType() {
		return businessType;
	}

	public void setBusinessType(byte businessType) {
		this.businessType = businessType;
	}

	public byte getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(byte companyNature) {
		this.companyNature = companyNature;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public long getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(long creatorTime) {
		this.creatorTime = creatorTime;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}