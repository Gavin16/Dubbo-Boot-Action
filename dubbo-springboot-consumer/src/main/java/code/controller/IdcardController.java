package code.controller;

import code.dto.IdcardDto;
import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.service.IdcardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("idcard")
public class IdcardController {

    @Reference(version = "1.0")
    IdcardService idcardService;

    @PostMapping("parse")
    public Result parseIdcard(@RequestBody IdcardDto dto){
        return idcardService.parseIdcard(dto.getIdcard());
    }
}
