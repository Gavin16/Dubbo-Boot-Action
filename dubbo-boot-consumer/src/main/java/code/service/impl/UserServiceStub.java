package code.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import test.beans.UserAddress;
import test.service.UserService;

import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 22:07
 * @Description:    调用远程之前可以先调用本地的存根代码
 *                  userService 本地存根实现;
 */
@Slf4j
public class UserServiceStub implements UserService {

    private final UserService userService;

    /**
     * 需要定义该构造方法 dubbo 框架会用到
     * @param userService
     */
    public UserServiceStub(UserService userService){
        super();
        this.userService = userService;
    }

    public List<UserAddress> getUserAddresses(String userId) {
        log.info("execute in UserServiceStub..");
        if(!StringUtils.isEmpty(userId)){
            return userService.getUserAddresses(userId);
        }
        return null;
    }
}
