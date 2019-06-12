package code.controller;

import code.dto.IdcardDto;
import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.service.IdcardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("idcard")
@Slf4j
public class IdcardController {

    @Reference(version = "1.1")
    IdcardService idcardService;

    @PostMapping("parse")
    public Result parseIdcard(@RequestBody IdcardDto dto){
        return idcardService.parseIdcard(dto.getIdcard());
    }

    @GetMapping("generate")
    public Result getRandomIdcard(){
        return idcardService.generateIdcard();
    }

}
