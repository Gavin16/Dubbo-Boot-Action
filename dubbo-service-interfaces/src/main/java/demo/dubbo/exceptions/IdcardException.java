package demo.dubbo.exceptions;

import demo.dubbo.enums.IdcardResultEnum;

public class IdcardException  extends ServiceException{

    /**
     * 这里必须要有无参构造方法, 否则消费端无法反序列化服务端抛出的异常
     * 将导致异常无法捕获
     */
    public IdcardException(){}

    public IdcardException(IdcardResultEnum resultEnum){
        super(resultEnum.getCode(),resultEnum.getMsg());
    }
}
