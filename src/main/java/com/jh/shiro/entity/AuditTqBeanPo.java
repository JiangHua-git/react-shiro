package com.jh.shiro.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AuditTqBeanPo {
    /**
     * 提取人姓名
     */
    private String xingming_tqr;
    /**
     * /**
     * 提取人标志
     */
    private String tqrbz;

    /**
     * 是否多人提取标志
     */
    private Integer sfdrtqbz;

    /**
     * 合同ftp路径
     */
    private String htFtpPath;

    /**
     * 提取人在公积金中心的个人编号
     */
    private String grbh_tqr;

    /**
     * 公积金提取操作人信息
     */
    private String gjjtqOperator;
    /**
     * 用于注册公积金中心（房产userid）
     */
    private Integer Userid1;
    /**
     * 公积金中心流程发起返回参数
     */
    private String dataResult;
    /**
     * userID公积金
     */
    private Integer useridgjj;
    /**
     * 流程id
     */
    private String taskId;
    /**
     * 流程参数
     */
    private String processInstanceId;
    /**
     * 提取人证件号码
     */
    private String zjhm_tqr;
    /**
     * 购房类型
     */
    @NotNull(message = "购房类型不允许为空")
    private String gflx;
    /**
     * 权证号(购房合同编号)
     */
    @NotNull(message = "购房合同编号不允许为空")
    private String gfhth;
    /**
     * 主购房人姓名
     */
    @NotNull(message = "主购房人姓名不允许为空")
    private String xingming_gfr;
    /**
     * 证件号码
     */
    @NotNull(message = "证件号码不允许为空")
    private String zjhm_gfr;
    /**
     * 房屋建筑面积
     */
    private Double fwmj;
    /**
     * 房屋总价
     */
    private Double fjk;
    /**
     * 购房项目名称
     */
    private String xmmc;
    /**
     * 房屋幢号
     */
    private String zhuanghao;
    /**
     * 预售许可证号
     */
    private String yskxz;
    /**
     * 单元号
     */
    private String dyh;
    /**
     * 房屋层号
     */
    private String fwch;
    /**
     * 房屋房间号
     */
    private String fjh;
    /**
     * 房屋坐落
     */
    private String fwxxdz;
    /**
     * 提取人类型
     */
    @NotNull(message = "提取人类型不允许为空")
    private String ygfrgx;
    /**
     * 契税发票号
     */
    private String qsfph;
    /**
     * 购房日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date gfrq;
    /**
     * 房屋已提取金额
     */
    private Double lnljtqbj;
    /**
     * 是否贷款
     */
    @NotNull(message = "是否贷款不允许为空")
    private String sfdk;
    /**
     * 个人账户余额
     */
    @NotNull(message = "个人账户余额不允许为空")
    private Double grzhye;
    /**
     * 个人月缴存额
     */
    @NotNull(message = "个人月缴存额不允许为空")
    private Double gryjce;
    /**
     * 可提取额
     */
    @NotNull(message = "可提取额不允许为空")
    private Double ktqe;
    /**
     * 冻结金额
     */
    @NotNull(message = "冻结金额购房类型不允许为空")
    private Double djje;
    /**
     * 本次提取额
     */
    private Double tqbj;
    /**
     * 交易方式 1
     */
    @NotNull(message = "交易方式购房类型不允许为空")
    private Integer jyfs;
    /**
     * 个人账号
     */
    @NotNull(message = "个人账号购房人购房类型不允许为空")
    private String grzh_gfr;
    /**
     * 提取材料编号
     */
    private String tqclbh;
    /**
     * 提取原因
     */
    @Value("0101")
    private String tqyy;
    /**
     * 业务流水id
     */
    private String bpmid;
    /**
     * 业务流水
     */
    private String ywlsh;
    /**
     * 收款人
     */
    @NotNull(message = "收款人不允许为空")
    private String zrzxqc;
    /**
     * 收款人银行账号
     */
    @NotNull(message = "收款人银行账号不允许为空")
    private String tqyhzh;
    /**
     * 收款银行
     */
    @NotNull(message = "收款银行不允许为空")
    private String skyh;
    /**
     * 代办人姓名
     */
    private String dbrxm;
    /**
     * 代办人证件号码
     */
    private String dbrzjhm;
    /**
     * 代办人手机号码
     */
    private String dbrsjhm;
    /**
     * 备注
     */
    private String beizhu;
    /**
     * 个人账号
     */
    @NotNull(message = "个人账号不允许为空")
    private String grzh;
    /**
     * 单位账号
     */
    @NotNull(message = "单位账号不允许为空")
    private String dwzh;
    /**
     * 个人编号
     */
    @NotNull(message = "个人编号不允许为空")
    private String grbh;
    /**
     * 单位编号
     */
    private String dwbh;
    /**
     * id
     */
    private String id;
    /**
     * 购房日期
     */
    private String htqdrq;
    /**
     * 传" "
     */
    @Value(" ")
    private String zlksrq;
    /**
     * 传" "
     */
    @Value(" ")
    private String zljsrq;
    /**
     * 传"1"
     */
    @Value("1")
    private String ydtqr;
    /**
     * 交易方式 01对私、03对公
     */
    @Value("01")
    private String dlrlx;
    /**
     * 购房类型
     */
    private String tqlxbm;
    /**
     * 提取人证件号码
     */
    private String zjhm;
    /**
     * 传" "
     */
    @Value(" ")
    private String dwmc;
    /**
     * 传" "
     */
    @Value(" ")
    private String xingming;
    /**
     * 提取方式 传"02"
     */
    @Value("02")
    private String tqfs;
    /**
     * 传" "
     */
    @Value(" ")
    private String dkhth;
    /**
     * 传0
     */
    @Value("0")
    private Double dkbj;
    /**
     * 传0
     */
    @Value("0")
    private Double dkye;
    /**
     * 传0
     */
    @Value("0")
    private Double yhbxje;
    /**
     * 传" "
     */
    @Value(" ")
    private String zlhtbh;
    /**
     * 传0
     */
    @Value("0")
    private Double tqlx;
    /**
     * 本次提取额
     */
    private Double tqjehj;
    /**
     * 传" "
     */
    @Value(" ")
    private String dlryhzh;
    /**
     * 传" "
     */
    @Value(" ")
    private String name;
    /**
     * 0
     */
    @Value("0")
    private Integer sfdgqtzh;
    /**
     * 0
     */
    @Value("0")
    private Integer qsjg;
    /**
     * "0" 流程标识
     */
    @Value("0")
    private String lcbz;
    /**
     * "gt"
     */
    @Value("fc")
    private String blqd;
    /**
     * 操作员id
     */
    private Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 操作员id
     */
    private String czyuserid;
    /**
     * 流程判断标志 传0
     */
    @Value("0")
    private Integer sptg;
    /**
     * 中心编码
     */
    private String zxbm;
    /**
     * 机构编码
     */
    private String jgbm;
    /**
     * 业务分类 传"02"
     */
    @Value("02")
    private String ywfl;
    /**
     * 业务类别 传"11"
     */
    @Value("11")
    private String ywlb;
    /**
     * 客户编号 传" "
     */
    @Value(" ")
    private String khbh;
    /**
     * 账户编号 传""
     */
    @Value(" ")
    private String zhbh;
    /**
     * 服务编码 传"01"
     */
    @Value("01")
    private String ffbm;
    /**
     * 传" "
     */
    @Value(" ")
    private String wf_counid;
    /**
     * 传" "
     */
    @Value(" ")
    private String jsid;
    /**
     * 机构编码
     */
    @Value(" ")
    private String khjgbm;
    /**
     * 传" "
     */
    @Value(" ")
    private String yhbm;
    /**
     * 传" 1"
     */
    @Value("1")
    private String sfjs;
    /**
     * 传" 01"
     */
    @Value("01")
    private String jspt;
    /**
     * 传" df"
     */
    @Value("df")
    private String jsfs;
    /**
     * 业务发送日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date ywfsrq;
    /**
     * 附言
     * 传值zjhm+"提取公积金"+tqbj+"元。"
     */
    private String fy;
    /**
     * 冲账标志 传" 01"
     */
    @Value("01")
    private String czbz;
    /**
     * 冲账业务流水号
     * 传"  "
     */
    @Value(" ")
    private String bczywlsh;
    /**
     * 传 提取人姓名+"【"+zjhm+"】"
     * +"购买住房提取"+ tqjehj +"元"
     */
    private String description;
    /**
     * 例如
     * user.getGrbh()+"_"+user.getZxbm()+"_00"
     */
    private String applyUser;
    /**
     * 传1
     */
    @Value("3")
    private Integer bllx;
    /**
     * tqjehj> ktqe 传1，否则传0
     */
    @Value("0")
    private Integer tqjebz;
    /**
     * 传" 0"
     */
    @Value("0")
    private String sftj;
    /**
     * 同参数applyUser
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 传"jcr_gmzftq_sp"
     */
    @Value("jcr_gmzftq_sp")
    private String processKey;
    /**
     * 传参数bpmid对应值
     */
    private String businessKey;
    /**
     * 传"柜台业务"
     */
    @Value("柜台业务")
    private String channgel;
    /**
     * 传"提取业务"
     */
    @Value("提取业务")
    private String source;
    /**
     * 传中心编码对应值
     */
    private String tenantId;
    /**
     * 返回信息
     */
    private String msg = "";
    /**
     * 返回值 返回值，0-成功，99-失败
     */
    private Integer ret = 0;

    /**
     * 房产中心机构编码
     */
    private String zxjgbm;
    /**
     * 行政区划编码
     */
    private String xzqhbm;

    /**
     * 首付款
     */
    private Double sfk = 0.0;
    /**
     * 房产柜台用户名
     */
    private String username;

    /**
     * 住建部中中心编码
     */
    private String zjbzxbm;

    /**
     * 公积金机构编码
     */
    private String gjjjgbm;
    /**
     * 公积金机构编码
     */
    private String grbh_gfr;
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页条数
     */
    private Long size;

    /**
     * 合同名称
     */
    private String htmc;

    /**
     * 银行账号
     */
    private String yhzh;

    /**
     * 收款银行名称
     */
    private String skyhmc;
    /**
     * 联行号
     */
    private String lhh;
    /**
     * 联行号
     */
    private String pdfYwlsh;
    /**
     * 流程请求结果
     */
    private String bpmresult;
}
