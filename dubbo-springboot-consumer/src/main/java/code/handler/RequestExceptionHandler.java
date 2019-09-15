package code.handler;

import demo.dubbo.common.Result;
import demo.dubbo.enums.ResultEnum;
import demo.dubbo.exceptions.AddressException;
import demo.dubbo.exceptions.IdcardException;
import demo.dubbo.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(value = IdcardException.class)
    @ResponseBody
    public Result handleIdcardException(IdcardException e) {
        log.error(e.getMsg());
        return ResultUtil.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(value = AddressException.class)
    @ResponseBody
    public Result handleAddressException(AddressException e) {
        log.error(e.getMsg());
        return ResultUtil.error(e.getCode(), e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    public Result handleAddressException(ValidationException e){
        log.error(e.getMessage());
        String validateMsg = null;
        // ValidationException 有多个子类,参数校验失败抛出的异常是 ConstraintViolationException
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException cve = (ConstraintViolationException)e;
            Set<ConstraintViolation<?>> constraintViolations = cve.getConstraintViolations();
            for(ConstraintViolation cv : constraintViolations){
                validateMsg = cv.getMessage();
                break;
            }

            return ResultUtil.error(ResultEnum.VALIDATION_FAILURE.getCode(), validateMsg);
        }
        return ResultUtil.error(ResultEnum.VALIDATION_FAILURE.getCode(),e.getMessage());
    }

    /**
     * 控制层返回的异常统一放在此处理
     * 匹配不同返回类型 返回不同的错误编码与描述
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        // 异常信息保存在 StringWriter 中
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));

        if (e instanceof AddressException) {
            AddressException addressException = (AddressException) e;
            log.error(writer.getBuffer().toString());
            return ResultUtil.error(addressException.getCode(), addressException.getMsg());
        }

        if (e instanceof IdcardException) {
            IdcardException idcardException = (IdcardException) e;
            log.error(writer.getBuffer().toString());
            return ResultUtil.error(idcardException.getCode(), idcardException.getMsg());
        }

        log.error(writer.getBuffer().toString());
        return ResultUtil.error(ResultEnum.QUERY_FAILURE);

    }

}
