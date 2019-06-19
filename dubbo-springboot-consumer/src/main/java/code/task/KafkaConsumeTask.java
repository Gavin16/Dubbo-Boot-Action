package code.task;

import code.utils.HttpUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import demo.dubbo.dto.response.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class KafkaConsumeTask {
    @Value("${sms.send.url}")
    private String reqUrl;

    @Value("${sms.account.sid}")
    private String sid;
    @Value("${sms.auth.token}")
    private String token;
    @Value("${sms.appId}")
    private String appid;
    @Value("${sms.template.id}")
    private String templateId;
    @Value("${sms.receiver.mobile}")
    private String mobile;

    @KafkaListener(topics = "interview-question")
    public void kafkaReceiving(ConsumerRecord<String,String> record){
        log.info("KafkaConsumeTask===kafkaReceiving====消费kafka消息=========");
        String value = record.value();

        if(StringUtils.isNotEmpty(value)){
            QuestionDTO questionDTO = JSON.parseObject(value, QuestionDTO.class);
            String param = questionDTO.getTheme()+","+questionDTO.getContent()+","+questionDTO.getDegree();
            Map<String,Object> requestParam = getParams(param);
            String post = HttpUtil.post(reqUrl, requestParam);
            if(null == post){
                log.error("kafka消费后, 短信消息发送失败...");
            }
        }
    }

    private Map<String, Object> getParams(String param) {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", sid);
        map.put("token", token);
        map.put("appid", appid);
        map.put("templateid", templateId);
        map.put("param", param);
        map.put("mobile", mobile);
        return map;
    }


}
