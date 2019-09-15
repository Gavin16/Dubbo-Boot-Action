package com.demo.dao.po;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Class: UserPO
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/15 15:26
 * @Version: v1.0
 */
@Data
public class UserPO {
    /** user 主键 id */
    private int id;
    /** 登录用户名 */
    private String loginName;

    private String userName;

    private String userPassword;

    private int userType;

    private String mobile;

    private String address;
}
