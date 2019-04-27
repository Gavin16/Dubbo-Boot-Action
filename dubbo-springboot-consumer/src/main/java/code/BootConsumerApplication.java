package code;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 19:30
 * @Description:
 */
@EnableDubbo
@SpringBootApplication
public class BootConsumerApplication {

    public static void main(String[] args){
        SpringApplication.run(BootConsumerApplication.class,args);
    }
}
