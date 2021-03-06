package com.demo.dao.repository;


import com.demo.dao.po.SysAreaPO;

public interface AreaRepository {

    SysAreaPO getLocationByCodeLv3(long areaCode);

    SysAreaPO getLocationByCodeLv2(long areaCode);

    Long getAreaCodeById(long id);
}
