package com.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class KafkaService {

    private Producer<String,String> producer;

    private final Properties props = new Properties();

    private String topic;

    public KafkaService(){
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.102:9092,192.168.0.102:9093,192.168.0.102:9094");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        // 这里重试的次数是否包含第一次? retries = 1 是否会再重试一次
        props.put(ProducerConfig.RETRIES_CONFIG, "2");
        // 这里每次发送消息的数量指的是 消息的字节数
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "1638");
        // linger.ms 参数就是控制消息发送延时行为的。该参数默认值是0 ，表示消息需要被立即发送，无须关心batch 是否己被填满
        props.put(ProducerConfig.LINGER_MS_CONFIG, "0");
        this.producer = new KafkaProducer<String, String>(props);
    }

    // 向broker 发送消息
    public boolean sendMsg(String topic,String value){
        try {
            ProducerRecord<String,String> record = new ProducerRecord<>(topic, value);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    log.info("KafkaService===sendMsg=== 消息:{} 发送成功",value);
                }
            });
            return true;
        } catch (Exception e) {
            log.error("KafkaService===sendMsg===Kafka消息发送异常:{}",e);
        }
        return false;
    }

    // 向broker 发送消息
    public boolean sendMsg(String value){
        // 默认向 interview-question 主题发送消息
        return sendMsg("interview-question",value);
    }

}
