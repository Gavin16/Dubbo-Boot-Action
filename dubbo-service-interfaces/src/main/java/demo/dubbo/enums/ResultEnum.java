package demo.dubbo.enums;

/**
 * @Title: ${FILE_NAME}
 * @Package: com.demo.enums
 * @Description:
 * @author: Minsky
 * @date: 2018/5/19 16:02
 */
public enum ResultEnum {

    SUCCESS("10000","调用成功"),
    QUERY_FAILURE("10001","系统调用失败");

    private String code;

    private String msg;

    ResultEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
