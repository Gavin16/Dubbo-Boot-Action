package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.constants.RequestConstants;
import com.demo.common.constants.ResponseConstants;
import com.demo.common.utils.ResultUtil;
import com.demo.manager.impl.GaodeApiManagerImpl;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.exceptions.ServiceException;
import demo.dubbo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service(version = "1.0",owner = "minksy")
@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private GaodeApiManagerImpl gaodeApiManager;

    private static final String VALUE_JSON = "json";

    /**
     * **调用高德地图接口,由于不知接口何时变更,返回结果使用JSONObject 封装**
     *
     * 解析单个地址
     * @param s
     * @return
     */
    @Override
    public Result parseAddress(String s) {
        AddressParam addressParam = new AddressParam();
        addressParam.setAddress(s);

        Result result = parseAddress(addressParam);

        return result;
    }

    @Override
    public Result parseAddress(AddressParam addressParam) throws ServiceException {
        Map<String,String> param = new HashMap<>();
        param.put(RequestConstants.GaodeServiceParam.KEY_ADDRESS,addressParam.getAddress());
        param.put(RequestConstants.GaodeServiceParam.KEY_OUTPUT,VALUE_JSON);

        JSONObject res = gaodeApiManager.geoEncodeService(param);
        // 高德地图调用失败
        if(null == res){
            log.error("AddressServiceImpl----parseAddress----高德地图解析接口调用失败");
            return null;
        }

        return ResultUtil.success(res.getJSONArray(ResponseConstants.GaodeServiceParam.KEY_GEOCODES));
    }
}
