package com.demo.schedule;

import com.demo.common.utils.DateUtil;
import com.demo.utils.RedisService;
import demo.dubbo.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaScheduleTask {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RedisService redisService;

    /**
     * 向kafka 推送消息的任务, 每两小时执行一次
     */
    @Scheduled(fixedRate = 20*DateUtil.SECOND)
    public void sendMessageToKafka(){

        log.info("start to execute schedule task...");

        Integer randomId = getRandomIdForQuestion();
    }


    /**
     * 获取ID的逻辑如下：
     *      首先判断是否是首次推送(查看redis中是否有对应的set)，若是首次推送则
     *          (*)查询总记录条数N,在redis中创建 包含(0 ,N-1 )的set集合,然后在其中随机取出一个数值,并在set中将它删除,取出的数值作为随机的ID
     *      若不是首次推送,判断set是否为空,为空代表已经将所有记录发送过一遍了,需要重新执行上述(*)
     *      若既不是首次推送, set也不为空,则从中随机取出一个数值,并将它从set中移除
     * @return
     */
    private Integer getRandomIdForQuestion() {
        return null;
    }
}
