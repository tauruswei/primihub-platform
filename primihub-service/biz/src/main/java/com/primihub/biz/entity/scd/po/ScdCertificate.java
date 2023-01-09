package com.primihub.biz.entity.scd.po;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author a
 * @since 2022-06-07
 */
@Data
@ToString
public class ScdCertificate {
    /**
     * 主健
     */
    private Long id;

    /**
     * 模版名称
     */
    private String name;

    /**
     * 属性值
     */
    private String attrs;

    /**
     * 证书
     */
    private String certificate;

    /**
     * 密钥
     */
    private String priKey;
    /**
     * 模版id
     */
    private Long tempId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 状态
     */
    private int status;
    /**
     * 文件路径
     */
    private String certPath;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
