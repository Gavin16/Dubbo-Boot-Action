package demo.dubbo.exceptions;

import demo.dubbo.enums.ResultEnum;

public class QuestionException extends ServiceException {

    public QuestionException(){}

    public QuestionException(ResultEnum resultEnum){
        super(resultEnum.getCode(),resultEnum.getMsg());
    }

}
