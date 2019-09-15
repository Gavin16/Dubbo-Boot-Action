package com.demo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Class: IdcardRecordDTO
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/2 7:29
 * @Version: v1.0
 */
@Data
public class IdcardRecordDTO {

    private String cardNo;

    private Date createTm;
    /** 创建花费时间(ms) */
    private Long costTm;
}
