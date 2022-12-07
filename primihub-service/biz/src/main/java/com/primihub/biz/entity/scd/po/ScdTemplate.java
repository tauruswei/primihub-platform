package com.primihub.biz.entity.scd.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
public class ScdTemplate {
    /**
     * 主健
     */
    private Long id;
    /**
     * 模版名称
     */
    private String name;
    /**
     * 模版属性
     */
    private String attrs;
    /**
     * 证书
     */
    private String certificate;
    /**
     * 私钥
     */
    private String priKey;
    /**
     * 状态
     */
    private int status;
    /**
     * 文件路径
     */
    private String tempPath;
    /**
     * 创建时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /**
     * 修改时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
}
