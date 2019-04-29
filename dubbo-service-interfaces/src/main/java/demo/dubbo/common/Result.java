package demo.dubbo.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer SUCCESS = 1;
    private static final Integer ERROR = 0;
    private String requestId;
    private Integer status;
    private Integer business;
    private String errorCode;
    private String errorMessage;
    private String date;
    private String version;
    private T obj;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    public Result(boolean success) {
        this.setSuccess(success);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T obj) {
        Result<T> result = new Result<>(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setDate(sdf.format(new Date()));
        result.setObj(obj);
        return result;
    }

    public static <T> Result<T> error(String errorMessage) {
        return error(errorMessage, null);
    }

    public static <T> Result<T> error(String errorMessage, String errorCode) {
        Result<T> result = new Result<>(false);
        result.setErrorMessage(errorMessage);
        result.setErrorCode(errorCode);
        return result;
    }

    public Result(String requestId, boolean success) {
        super();
        this.requestId = requestId;
        this.setSuccess(success);
    }

    public boolean isSuccess() {
        if (null == status) {
            return false;
        }
        return status.intValue() == SUCCESS.intValue();
    }

    public void setSuccess(boolean success) {
        this.status = success ? SUCCESS : ERROR;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        setSuccess(false);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}