package code.controller;

import bean.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.OrderService;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: Minsky
 * @Date: 2019/2/17 19:54
 * @Description:
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("initOrder")
    @ResponseBody
    public List<UserAddress> initOrder(@RequestParam("userId") String userId) throws IOException {
        log.info("传入参数: {}",userId);
        return orderService.initOrder(userId);
    }
}
