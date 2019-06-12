package com.demo.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 参数验证工具类: 适用于服务端将传参copy到新的bean的情况,在新的bean中定义非参数的校验规则时可以使用
 * 该工具类提供的 validate 方法来验证参数
 */
public class ValidationUtil {

    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class)
                        .configure().failFast(true).buildValidatorFactory().getValidator();


    /**
     * 功能描述: <br>
     * 〈注解验证参数〉
     * @param obj obj
     */
    public static <T> String validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        // 检验不合格处理
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            List<String> tipList = new ArrayList<>();
            constraintViolations.forEach(constraintViolationImpl -> tipList.add(constraintViolationImpl.getMessage()));
            return StringUtils.join(tipList, ",");
        } else {
            return "";
        }

    }
}
