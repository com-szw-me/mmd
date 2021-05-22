package com.szw.me.mmd.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
@Data
public class RoleAccess implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 权限id
     */
    private Integer aid;

    /**
     * 编辑时间
     */
    private Date mtime;

    /**
     * 编辑用户
     */
    private Integer muid;

    private static final long serialVersionUID = 1L;
}