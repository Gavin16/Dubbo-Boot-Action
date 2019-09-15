package code.filters;

import code.config.AuthorUrlConfig;
import code.utils.RedisService;
import demo.dubbo.common.Constants;
import demo.dubbo.enums.ResultEnum;
import demo.dubbo.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Class: LoginFilter
 * @Description:
 *
 *    增加登录模块，对调用接口用户做鉴权处理
 * @Author: Minsky
 * @Date: 2019/9/9 9:21
 * @Version: v1.0
 */
@Slf4j
@WebFilter(filterName = "sessionFilter" ,urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthorUrlConfig authorUrlConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 从request 中获取sessionId,判断是否登录
        HttpServletRequest sevletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = sevletRequest.getSession();
        // 放行登录接口
        String path = sevletRequest.getRequestURI();
        log.info("LoginFilter---doFilter---访问路径为：{}",path);
        // TODO 判断当前路径是否是需要放行的路径
        if(!authorUrlConfig.contains(path)){
            String sessionId = session.getId();
            boolean isExists = redisService.hasKey(sessionId);
            if(isExists){
                chain.doFilter(request,response);
            }

            // 返回让客户端走登录接口
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.getWriter().write(ResultUtil.error(ResultEnum.USER_NOT_LOGIN).toString());
        }if(Constants.Login.loginUrl.equals(path)){
            // 若判断是登录接口， 则登录完之后将sessionId保存到response中
            chain.doFilter(request,response);
            HttpServletResponse loginResponse = (HttpServletResponse) response;


        }else{
            // 无需登录,直接调用的接口
            chain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
