package com.demo.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.utils.HttpUtil;
import com.demo.manager.GaodeApiManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class GaodeApiManagerImpl implements GaodeApiManager {


    private static final Logger logger = LoggerFactory.getLogger(GaodeApiManagerImpl.class);

    @Value("${gaode.addrParse.key}")
    private String appkey;

    private static final String KEY = "key";

    /**
     * 高德地理编码 API 服务
     * @param url
     * @param param
     * @return
     */
    @Override
    public JSONObject geoEncodeService(String url, Map<String,String> param){
        // 添加调用 appKey
        param.put(KEY,appkey);
        logger.info("高德地理编码接口调用传参为：{}",param);
        String res = HttpUtil.get(url,param);
        logger.info("高德地理编码接口返回结果：{}",res);
        if(StringUtils.isNotEmpty(res)){
            JSONObject jsonRes = JSON.parseObject(res);
            return jsonRes;
        }
        return null;
    }
}
