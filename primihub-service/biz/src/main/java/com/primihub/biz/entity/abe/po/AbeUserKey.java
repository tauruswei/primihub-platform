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
public class AbeUserKey {
    /**
     * 主健
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * attributes
     */
    private String attrs;

    /**
     * 用户密钥
     */
    private String sk;

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
