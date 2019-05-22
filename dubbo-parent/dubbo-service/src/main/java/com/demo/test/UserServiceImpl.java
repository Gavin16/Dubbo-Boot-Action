package com.demo.test;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.manager.config.ManagerPropertyConfig;
import com.demo.service.AddressServiceImpl;
import demo.dubbo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.beans.UserAddress;
import test.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 09:30
 * @Description:
 */
@Service(version = "1.1",owner = "minsky",timeout = 10000)
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private ManagerPropertyConfig config;

    @Autowired
    private AddressServiceImpl addressService;

    public List<UserAddress> getUserAddresses(String userId) {
        UserAddress address1 = new UserAddress("1","深圳市南山区鸿瑞花园","123","zhsan","18812345678","1");
        UserAddress address2 = new UserAddress("2","深圳市南山区桂庙新村","223","zhsan","18812345678","0");
        System.out.println("UserServiceImpl--getUserAddresses 正在调用...");

//        System.out.println("config: key = "+ config.getKey());

        Result result = addressService.parseAddress("深圳市南山区鸿瑞花园");
        System.out.println("test result: " + result.getObj());


        return Arrays.asList(address1,address2);
    }
}
