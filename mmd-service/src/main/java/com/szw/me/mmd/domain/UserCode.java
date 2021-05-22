package com.szw.me.mmd.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class UserCode implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 流水
     */
    private Integer index;

    private static final long serialVersionUID = 1L;
}