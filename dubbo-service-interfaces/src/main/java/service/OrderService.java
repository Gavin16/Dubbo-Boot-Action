package service;

import bean.UserAddress;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 09:38
 * @Description:
 */
public interface OrderService {

    /**
     * 初始化订单
     * @param userId
     */
    List<UserAddress> initOrder(String userId) throws IOException;
}
