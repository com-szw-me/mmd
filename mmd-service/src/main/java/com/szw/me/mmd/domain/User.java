package com.szw.me.mmd.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author 
 * 
 */
@Data
@Document(indexName = "user")
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 编号
     */
    private String code;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

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