package code.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

/**
 * @Class: AuthorUrlConfig
 * @Description:
 *
 * 无需登录就可访问的接口配置
 * @Author: Minsky
 * @Date: 2019/9/15 14:46
 * @Version: v1.0
 */
@Configuration
@PropertySource(value = "directAccessUrls.properties")
public class AuthorUrlConfig {

    @Value("${directUrls}")
    private Set<String> directUrls;


    public boolean contains(String url){
        return directUrls.contains(url);
    }
}
