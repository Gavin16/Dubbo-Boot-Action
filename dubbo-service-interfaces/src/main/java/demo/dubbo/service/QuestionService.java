package demo.dubbo.service;

import demo.dubbo.common.Result;
import demo.dubbo.dto.response.QuestionOptionDto;

/** 将数据库中面试信息按定时任务推送给kafka服务器 */
public interface QuestionService {

    /**
     * consumer指定推送的记录编号
     * @param id
     * @return
     */
    Result sendQuestionToKafka(Integer id);

    /**
     * 服务提供端按自己的方式推送单条面试题记录到kafka服务器
     * @return
     */
    Result sendQuestionToKafka();

    /**
     * 若kafka消息推送成功,对该记录推送次数加1
     * @param id
     * @return
     */
    Result incrRemindTimes(Integer id);

    /**
     * 从文件中读取面试题导入到数据库; 仅限研发环境使用
     * @param dto
     * @return
     */
    Result importQuestionFromFile(QuestionOptionDto dto);

    /**
     * 从redis缓存中删除 短信发送过的ID
     * @param id
     * @return
     */
    Long remSendedIdFromRedisCache(String id);
}
