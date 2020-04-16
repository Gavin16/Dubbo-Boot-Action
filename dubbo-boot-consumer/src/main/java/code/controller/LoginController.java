package code.controller;

import code.utils.MD5Util;
import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.LoginDto;
import demo.dubbo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class: UserController
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/8 19:18
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("user")
public class LoginController {

    @Reference(version = "1.0")
    private LoginService loginService;

    /**
     * 登录操作
     * @param dto
     * @return
     */
    @PostMapping("login")
    public Result userLogin(@RequestBody LoginDto dto){
        // TODO 根据用户名和密码 从user表中获取用户信息

        // 用户名密码采用MD5 加密
        String md5pass = MD5Util.MD5(dto.getUserPassword());
        dto.setUserPassword(md5pass);
        // 若数据库成功获取用户，将用户数据存redis 并设置30分钟过期时间
        return loginService.login(dto);
    }
}
