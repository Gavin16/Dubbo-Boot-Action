package com.demo.service;

import com.demo.common.constants.Constants;
import com.demo.common.utils.FileUtil;
import com.demo.common.utils.ResultUtil;
import com.demo.dao.po.QuestionPO;
import com.demo.dao.repository.QuestionRepository;
import com.demo.utils.RedisService;
import demo.dubbo.common.Result;
import demo.dubbo.dto.response.QuestionOptionDto;
import demo.dubbo.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public Result sendQuestionToKafka(Integer id) {
        return null;
    }

    @Override
    public Result sendQuestionToKafka() {
        return null;
    }

    @Override
    public Result incrRemindTimes(Integer id) {
        return null;
    }


    @Override
    public Result importQuestionFromFile(QuestionOptionDto paramDto) {

        String text = FileUtil.readFile(paramDto.getPath());
        String source = StringUtils.isEmpty(paramDto.getSource())? "ydym" : paramDto.getSource();
        String[] lines = text.split("\\r?\\n");
        // 数据封装到PO的list中
        List<QuestionPO> poList = new ArrayList<>();

        for(String line : lines){
            if(StringUtils.isEmpty(line))
                continue;
            QuestionPO dto = parseLineToPO(line.trim(),source);
            poList.add(dto);
        }
        // 数据插入数据库表
        questionRepository.batchInsert(poList);
        // 返回调用结果
        return ResultUtil.success(null);
    }

    @Override
    public Long remSendedIdFromRedisCache(String id) {
        return redisService.srem(Constants.RedisPrefix.QUESTION_ID,id);
    }


    private QuestionPO parseLineToPO(String line,String source) {

        if(!StringUtils.isEmpty(line)){
            QuestionPO questionPO = new QuestionPO();
            String[] elements = line.split("--");
            questionPO.setTheme(elements[0]);
            questionPO.setContent(elements[1]);
            questionPO.setDegree(elements[2]);
            questionPO.setSource("ydym");
            return questionPO;
        }
        return null;
    }


}
