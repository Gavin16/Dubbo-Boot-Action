package code.controller;

import code.dto.IdcardDto;
import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.service.IdcardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("idcard")
@Slf4j
public class IdcardController {

    @Reference(version = "1.0")
    IdcardService idcardService;

    @PostMapping("parse")
    public Result parseIdcard(@RequestBody IdcardDto dto){
        Result result = null;
        return idcardService.parseIdcard(dto.getIdcard());
    }
}
