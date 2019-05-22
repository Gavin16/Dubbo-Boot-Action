package demo.dubbo.service;

import demo.dubbo.common.Result;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.exceptions.ServiceException;

public interface AddressService {

    /**
     * 解析格式化的地址：地址可以不包含省市 但是地址位置要求唯一
     * @param formatedAddress
     * @return
     */
    Result parseAddress(String formatedAddress);


    /**
     *
     * @param addressParam
     * @return
     */
    Result parseAddress(AddressParam addressParam) throws ServiceException;

}
