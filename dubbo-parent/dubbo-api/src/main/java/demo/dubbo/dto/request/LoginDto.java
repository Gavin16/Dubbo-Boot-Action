package demo.dubbo.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
