package com.szw.me.mmd.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
@Data
public class Role implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 父角色id
     */
    private Integer pid;

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