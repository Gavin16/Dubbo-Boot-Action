package code;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 19:30
 * @Description:
 *
 * 项目克隆下来之后,如果在多个窗口打开可能遇到
 * (1)各包所在目录不是git根目录的问题, 只需要在event log 中add root即可
 * (2) 打开项目是可能看不到源码,可以在右边侧边 maven projects 栏中  点击"Generata Sources and Update Folders For All Projects" 即可
 */
@EnableDubbo
@SpringBootApplication
public class BootConsumerApplication {

    public static void main(String[] args){
        SpringApplication.run(BootConsumerApplication.class,args);
    }
}
