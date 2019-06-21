package com.demo.schedule;

import com.alibaba.fastjson.JSON;
import com.demo.common.constants.Constants;
import com.demo.common.utils.DateUtil;
import com.demo.dao.po.QuestionPO;
import com.demo.dao.repository.QuestionRepository;
import com.demo.utils.KafkaService;
import com.demo.utils.RedisService;
import demo.dubbo.dto.response.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时向kafka 发送消息
 */
@Slf4j
@Component
public class KafkaScheduleTask {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private KafkaService kafkaService;

    /**
     * 向kafka 推送消息的任务, 每两小时执行一次
     */
    @Scheduled(fixedRate = 1* DateUtil.HOUR)
    public void sendMessageToKafka(){

        log.info("start to execute schedule task...");
        // 获取一个随机的ID,ID均为 大于0 的数
        Integer randomId = getRandomIdForQuestion();
        // 若 randomId 不大于0 代表表中数据为空,不执行任何逻辑
        if(randomId > 0){
            // 查询获得
            QuestionPO po = questionRepository.getById(randomId);
            log.info("查询需要推送的面试题为:{}", po);

            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(po,dto );

            kafkaSending(dto,randomId);
        }
    }

    /**
     * 向kafka 发送数据
     * @param dto
     */
    private void kafkaSending(QuestionDTO dto,Integer id) {
        boolean isSuccess = kafkaService.sendMsg(JSON.toJSONString(dto));
        // 若消息推送成功则从集合中删除 id
        if(isSuccess){
            log.info("KafkaScheduleTask===kafkaSending===向kafka发送数据成功");
        }
    }


    /**
     * 获取ID的逻辑如下：
     *      首先判断是否是首次推送(查看redis中是否有对应的set)，若是首次推送则
     *          (*)查询总记录条数N,在redis中创建 包含(1 ,N)的set集合,然后在其中随机取出一个数值,取出的数值作为随机的ID
     *      若不是首次推送,判断set是否为空,为空代表已经将所有记录发送过一遍了,需要重新执行上述(*)
     *      若既不是首次推送, set也不为空,则从中随机取出一个数值
     * @return
     */
    private Integer getRandomIdForQuestion() {
        String questionRedisKey = Constants.RedisPrefix.QUESTION_ID;

        Boolean hasKey = redisService.strictHasKey(questionRedisKey);
        Long left = redisService.scard(questionRedisKey);
        // 不存在key 或者 集合中 剩余元素为0
        String id;
        if(!hasKey || left ==0){
            int totalNum = questionRepository.totalRecordsNum();
            String[] idsArray = generateIdStrArray(totalNum);
            // 表中没有数据
            if(idsArray.length  == 0){
                return 0;
            }
            redisService.sAdd(questionRedisKey,idsArray);

            // 这里获取ID后暂时不删除,等到确定kafka消息成功推送到broker 再删除
            id = redisService.sRandMember(questionRedisKey);
        }else{
            // 这里获取ID后暂时不删除,等到确定kafka消息成功推送到broker 再删除
            id = redisService.sRandMember(questionRedisKey);
        }
        return Integer.parseInt(id);
    }

    /**
     * 生成面试题记录 id 数组 用来传给redis 集合
     * @return
     */
    private static String[] generateIdStrArray(int totalNum) {

        StringBuilder sb = new StringBuilder(totalNum);
        for(int i = 0; i < totalNum ; i++){
            if(i == totalNum - 1){
                sb.append(i+1);
            }else{
                sb.append(i+1).append(",");
            }
        }
        String[] strArr = sb.toString().split(",");
//        String arrStr = Arrays.toString(idArr);
        // 考虑怎么让 "[" 与 "]" 的匹配用一个正则表达式实现
//        String[] strArr = arrStr.replace("[", "").replace("]","").split(",");
//        String[] strArr = arrStr.replaceAll("[\\[\\]]","").split(",");
        return strArr;
    }
}
