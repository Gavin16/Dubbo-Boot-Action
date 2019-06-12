package code.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.AddressDto;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("address")
public class AddressController {

    @Reference(version = "1.1",validation = "true")
    private AddressService addressService;

    @PostMapping("parse")
    public Result parseAddress(@RequestBody AddressParam param){

        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(param, dto);

        log.info("AddressController---parseAddress---入参：{}",dto);
        Result result = addressService.parseAddress(dto);
        return result;
    }
}

