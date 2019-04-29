package demo.dubbo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.exceptions.ServiceException;

public interface AddressService {

    /**
     * 解析格式化的地址：地址可以不包含省市 但是地址位置要求唯一
     * @param formatedAddress
     * @return
     */
    JSONObject parseAddress(String formatedAddress);


    /**
     *
     * @param addressParam
     * @return
     */
    JSONArray parseAddress(AddressParam addressParam) throws ServiceException;

}
