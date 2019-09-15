package demo.dubbo.service;

import demo.dubbo.common.Result;
import demo.dubbo.dto.request.LoginDto;

/**
 * @Class: LoginService
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/15 14:57
 * @Version: v1.0
 */
public interface LoginService {

    /**
     * 登录
     * @param loginDto
     * @return
     */
    Result login(LoginDto loginDto);


    /**
     * 注销
     * @param logoutDto
     * @return
     */
    Result logout(LoginDto logoutDto);
}
