package com.primihub.biz.entity.abe.po;

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
public class AbeProject {
    /**
     * 主健
     */
    private Long id;

    /**
     * 项目名称
     */
    private String name;
    /**
     * pk 公钥
     */
    private String pk;

    /**
     * msk 主密钥
     */
    private String msk;

    /**
     * 状态
     */
    private int status;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
