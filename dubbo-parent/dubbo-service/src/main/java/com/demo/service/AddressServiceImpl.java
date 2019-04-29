package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.exceptions.ServiceException;
import demo.dubbo.service.AddressService;
import org.springframework.stereotype.Component;

@Service(version = "1.0")
@Component
public class AddressServiceImpl implements AddressService {
    @Override
    public JSONObject parseAddress(String s) {
        return null;
    }

    @Override
    public JSONArray parseAddress(AddressParam addressParam) throws ServiceException {
        return null;
    }
}
