package com.demo.manager.mq;

import com.alibaba.fastjson.JSON;
import demo.dubbo.dto.response.IdcardDTO;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;

public class RocketMQProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer();

        producer.setProducerGroup("test_mq_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        IdcardDTO idcardDTO = new IdcardDTO();
        idcardDTO.setGender("male");
        idcardDTO.setAreaFullName("深圳市福田区景田社区");
        idcardDTO.setBirthday(new Date());
        for(int i = 0 ; i < 3 ;i++){
            final int index = i;
            Message msg = new Message("TopicTest", "TagA", "OrderID188" + index, JSON.toJSONString(idcardDTO).getBytes());

            // RocketMq 生产者有三种发送类型：同步,异步，单向发送
            if(i == 0){
                // 同步发送：指的发送到服务器之后会一直阻塞等待服务器应答之后才之后后续的逻辑
                SendResult result = producer.send(msg);
                System.out.println("Send Synchronously result: " + result);
            }else if(i == 1){
                // 异步发送：发送到服务器之后立刻执行后面的逻辑，当服务器接收到消息之后回调接口通知生产者

                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }
                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            }else{
                // 单向发送：生产者发送完消息之后不再接收服务器的应答和回调(相对不够可靠，但是效率高)
                producer.sendOneway(msg);
                System.out.println("Send One-Way finished...");
            }
        }
    }
}
