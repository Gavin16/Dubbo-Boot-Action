package com.demo.dao.repository;

import com.demo.dao.po.UserPO;

/**
 * @Class: LoginRepository
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/15 15:25
 * @Version: v1.0
 */
public interface LoginRepository {

    /** 登录/登出匹配用户名和密码 使用同一方法 */
    UserPO loginInByNameAndPass(UserPO dto);

}
