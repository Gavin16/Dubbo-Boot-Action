package code.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import test.beans.UserAddress;
import test.service.OrderService;
import test.service.UserService;

import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 09:39
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 调用远程之前会先调用本地的存根代码 由stub 属性配置
     */
    @Reference(version = "1.1")
    UserService userService;

    public List<UserAddress> initOrder(String userId){
       return userService.getUserAddresses(userId);
    }
}
