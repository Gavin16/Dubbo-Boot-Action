package test.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 09:24
 * @Description:
 */
@Data
public class UserAddress implements Serializable {
    private String id;
    /** 用户地址 */
    private String userAddress;
    /** 用户id*/
    private String userId;
    /** 收货人*/
    private String consignee;
    /** 电话号码*/
    private String phoneNum;
    /** 是否默认地址 0-否 1-是*/
    private String isDefault;

    public UserAddress(){}

    public UserAddress(String id, String userAddress, String userId,
                       String consignee, String phoneNum, String isDefault){
        this.id = id;
        this.userAddress = userAddress;
        this.userId = userId;
        this.phoneNum = phoneNum;
        this.isDefault = isDefault;
        this.consignee = consignee;
    }
}
