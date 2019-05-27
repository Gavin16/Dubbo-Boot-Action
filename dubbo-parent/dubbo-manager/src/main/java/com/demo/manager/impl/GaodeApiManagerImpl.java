package com.demo.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.utils.HttpUtil;
import com.demo.manager.GaodeApiManager;
import com.demo.manager.config.ManagerPropertyConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class GaodeApiManagerImpl implements GaodeApiManager {

    private static final Logger logger = LoggerFactory.getLogger(GaodeApiManagerImpl.class);

    @Autowired
    private ManagerPropertyConfig managerPropertyConfig;

    private static final String KEY = "key";

    /**
     * 高德地理编码 API 服务
     * @param param
     * @return
     */
    @Override
    public JSONObject geoEncodeService(Map<String,String> param){
        String appkey = managerPropertyConfig.getKey();
        String url = managerPropertyConfig.getUrl();

        param.put(KEY,appkey);
        logger.info("高德地理编码接口调用传参为：{}",param);
        String res = HttpUtil.get(url,param);
        logger.info("高德地理编码接口返回结果：{}",res);
        // 判断是否调用异常 是否调用成功
        if(StringUtils.isNotEmpty(res) && isSuccess(res)){
            JSONObject jsonRes = JSON.parseObject(res);
            return jsonRes;
        }
        return null;
    }



    /**
     * 高德地图接口是否调用成功
     * @param res
     * @return
     */
    private boolean isSuccess(String res) {
        JSONObject obj = JSON.parseObject(res);

        String status = obj.getString("status");
        Integer count = obj.getInteger("count");

        if("1".equals(status) && count > 0){
            return true;
        }
        return false;
    }
}
