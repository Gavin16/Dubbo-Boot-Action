package demo.dubbo.exceptions;

public class ServiceException extends RuntimeException{

    private String errorCode;
    private String errorMessage;

    public ServiceException(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceException(String msg, Throwable e){
        super(msg,e);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
