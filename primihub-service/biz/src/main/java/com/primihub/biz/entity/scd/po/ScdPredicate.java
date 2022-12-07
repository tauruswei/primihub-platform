package com.primihub.biz.entity.scd.po;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ScdPredicate {
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 类型 EQ,GE,LE,GT,LT
     */
    private String type;
    /**
     * 值
     */
    private int value;

}
