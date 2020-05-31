package demo.dubbo.dto.request;

import lombok.Data;


/**
 * @Class: UserDto
 * @Description:  登录之后返回给 session及调用端使用的DTO
 * @Author: Minsky
 * @Date: 2019/9/15 15:43
 * @Version: v1.0
 */
@Data
public class UserDto {
    /** user 主键 id */
    private int id;

    /** 登录用户名 */
    private String loginName;

    private String userName;

    private String userPassword;

    private int userType;

    private String mobile;

    private String address;
}
