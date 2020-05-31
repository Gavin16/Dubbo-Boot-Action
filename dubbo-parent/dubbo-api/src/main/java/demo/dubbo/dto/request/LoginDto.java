package demo.dubbo.dto.request;

import lombok.Data;


/**
 * @Class: LoginDto
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/15 15:14
 * @Version: v1.0
 */
@Data
public class LoginDto  {
    /** user 主键 id */
    private int id;

    private String loginName;

    private String userName;

    private String userPassword;

    private int userType;

    private String mobile;

    private String address;
}
