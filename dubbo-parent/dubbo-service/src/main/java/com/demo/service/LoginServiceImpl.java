package com.demo.service;

import com.demo.common.utils.ResultUtil;
import com.demo.dao.po.UserPO;
import com.demo.dao.repository.LoginRepository;
import com.demo.utils.RedisService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import demo.dubbo.common.Constants;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.LoginDto;
import demo.dubbo.dto.request.UserDto;
import demo.dubbo.enums.ResultEnum;
import demo.dubbo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Class: LoginServiceImpl
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/15 15:07
 * @Version: v1.0
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RedisService redisService;

    /**
     * 查询数据库判断用户名和密码是否匹配，若匹配则将用户数据保存到 共享session 若不匹配则返回用户名或密码错误
     * @param userDto
     * @return
     */
    @Override
    public Result login(LoginDto userDto) {
        UserPO po = new UserPO();
        BeanUtils.copyProperties(userDto,po);

        UserPO queryRes = loginRepository.loginInByNameAndPass(po);
        if(null != queryRes){
            // 用户信息保存到session
            addUserToSession(queryRes);
        }
        // 提示用户名或者密码错误
        return ResultUtil.error(ResultEnum.LOGINNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 用户信息按需要添加到session
     * @param queryRes
     */
    private void addUserToSession(UserPO queryRes) {
        UserDto userInfo = new UserDto();
        BeanUtils.copyProperties(queryRes,userInfo);

        String sessionKey = Constants.Login.sessionPrefix + userInfo.getLoginName();
        HashMap<String,String> fields = fieldsToKeyValues(userInfo);

        redisService.hmset(sessionKey,fields);
    }

    private HashMap<String, String> fieldsToKeyValues(UserDto userInfo) {
        return null;
    }


    @Override
    public Result logout(LoginDto userDto) {
        return null;
    }
}
