package code.service.impl;

import bean.UserAddress;
import org.springframework.util.StringUtils;
import service.UserService;

import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 22:07
 * @Description:    调用远程之前可以先调用本地的存根代码
 *                  userService 本地存根实现;
 */
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
        System.out.println("UserServiceStub");
        if(!StringUtils.isEmpty(userId)){
            return userService.getUserAddresses(userId);
        }
        return null;
    }
}
