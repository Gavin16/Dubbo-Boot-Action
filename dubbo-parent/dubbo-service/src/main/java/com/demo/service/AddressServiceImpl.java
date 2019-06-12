package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.constants.RequestConstants;
import com.demo.common.constants.ResponseConstants;
import com.demo.common.utils.ResultUtil;
import com.demo.common.utils.ValidationUtil;
import com.demo.manager.impl.GaodeApiManagerImpl;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.AddressDto;
import demo.dubbo.enums.ResultEnum;
import demo.dubbo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service(version = "1.1",owner = "minksy")
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
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress(s);

        Result result = parseAddress(addressDto);

        return result;
    }

    @Override
    public Result parseAddress(@NotNull AddressDto dto){
        // 先对参数做校验,若参数不合法则直接返回
        String validate = ValidationUtil.validate(dto);
        if(!StringUtils.isEmpty(validate)){
            return ResultUtil.error(ResultEnum.VALIDATION_FAILURE.getCode(),validate);
        }

        Map<String,String> param = new HashMap<>();
        param.put(RequestConstants.GaodeServiceParam.KEY_ADDRESS,dto.getAddress());
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
