package demo.dubbo.exceptions;

import demo.dubbo.enums.AddressResultEnum;

public class AddressException extends ServiceException {

    /**
     * 这里必须要有无参构造方法, 否则消费端无法反序列化服务端抛出的异常
     * 将导致异常无法捕获
     */
    public AddressException(){}

    public AddressException(AddressResultEnum resultEnum){
        super(resultEnum.getCode(),resultEnum.getMsg());
    }

    public AddressException(String errorCode, String msg){
        super(errorCode,msg);
    }
}
