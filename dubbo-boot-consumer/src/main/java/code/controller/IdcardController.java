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

    @Reference(version = "1.3")
    IdcardService idcardService;

    @PostMapping("parse")
    public Result parseIdcard(@RequestBody IdcardDto dto){
        log.info("IdcardController===parseIdcard=== 入参：{}",dto);
        return idcardService.parseIdcard(dto.getIdcard());
    }

    @GetMapping("generate")
    public Result getRandomIdcard(){
        log.info("IdcardController===getRandomIdcard===方法执行");
        return idcardService.generateIdcard();
    }

}
