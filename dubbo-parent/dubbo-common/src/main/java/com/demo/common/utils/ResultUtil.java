package com.demo.common.utils;

import com.demo.common.enums.IdcardResultEnum;
import com.demo.common.enums.ResultEnum;
import demo.dubbo.common.Result;

public class ResultUtil {


    public static Result success(Object obj) {
        Result result = new Result(true);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMsg());
        result.setObj(obj);
        return result;
    }


    public static Result error(String code ,String msg){
        Result result = new Result(false);
        result.setCode(code);
        result.setMessage(msg);
        result.setObj(null);
        return result;
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result(false);
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMsg());
        result.setObj(null);
        return result;
    }

    public static Result error(IdcardResultEnum resultEnum) {
        Result result = new Result(false);
        result.setMessage(resultEnum.getMsg());
        result.setCode(resultEnum.getCode());
        result.setObj(null);
        return result;
    }
}
