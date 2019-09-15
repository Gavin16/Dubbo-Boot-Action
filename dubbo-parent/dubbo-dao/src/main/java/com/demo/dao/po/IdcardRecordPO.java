package com.demo.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @Class: IdcardRecordPO
 * @Description: TODO
 * @Author: Minsky
 * @Date: 2019/9/2 7:34
 * @Version: v1.0
 */
@Data
public class IdcardRecordPO {
    private String cardNo;

    private Date createTm;
    /** 创建花费时间(ms) */
    private Long costTm;
}
