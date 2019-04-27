package service;


import bean.UserAddress;

import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 09:17
 * @Description:
 */
public interface UserService {

    /**
     * 由 userId 获取user 收货地址列表
     * @param userId
     * @return
     */
    List<UserAddress> getUserAddresses(String userId);
}
