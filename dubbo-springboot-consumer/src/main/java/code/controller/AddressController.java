package code.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("address")
public class AddressController {

    @Reference(version = "1.0")
    private AddressService addressService;

    @PostMapping("parse")
    public Result parseAddress(@RequestBody AddressParam param){
        log.info("AddressController---parseAddress---入参：{}",param);
        Result result = addressService.parseAddress(param.getAddress());
        return result;
    }
}

