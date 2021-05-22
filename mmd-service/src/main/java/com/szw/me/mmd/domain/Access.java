package com.szw.me.mmd.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
@Data
public class Access implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限点名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 编辑时间
     */
    private Date mtime;

    private static final long serialVersionUID = 1L;
}