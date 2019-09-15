package com.demo.utils;

import com.alibaba.fastjson.JSON;
import com.demo.dao.po.IdcardRecordPO;
import com.demo.dao.repository.IdcardRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Class: KafkaIdcardRecordService
 * @Description:
 *
 *  TODO 采用SpringMVC 的配置方式消费 指定主题的kafka消息
 * @Author: Minsky
 * @Date: 2019/9/2 7:45
 * @Version: v1.0
 */
@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private IdcardRepository idcardRepository;

    @KafkaListener(topics = "idcard-record")
    public void idRecordSaveToDb(ConsumerRecord<String,String> record) {
        String value = record.value();
        log.info("KafkaConsumer===idRecordSaveToDb====消费kafka消息=========value:{}",value);
        try {
            IdcardRecordPO json = JSON.parseObject(value,IdcardRecordPO.class);
            if(json != null){
                idcardRepository.insertRecord(json);
            }
        } catch (Exception e) {
            log.info("KafkaConsumer===idRecordSaveToDb====消费消息写入DB异常:{}",e);
        }
    }
}

