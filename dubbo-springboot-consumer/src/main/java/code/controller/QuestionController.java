package code.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.dto.response.QuestionOptionDto;
import demo.dubbo.service.QuestionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("question")
public class QuestionController {

    @Reference(version = "1.0",validation = "true")
    private QuestionService questionService;

    @RequestMapping("batchInsert")
    public Result batchInsert(@RequestBody QuestionOptionDto dto){
        return questionService.importQuestionFromFile(dto);
    }
}


