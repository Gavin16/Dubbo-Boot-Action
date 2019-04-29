package demo.dubbo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Minsky
 * @Date: 2019/1/5 15:06
 * @Description:
 */
@Data
public class AddressParam implements Serializable {

    private String address;

    private String city;

    private Boolean batch;

}
