package demo.dubbo.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "登录名不能为空")
    private String loginName;

    private String userName;

    @NotNull(message = "登录密码不能为空")
    private String userPassword;

    private int userType;

    private String mobile;

    private String address;
}
