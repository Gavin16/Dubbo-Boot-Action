package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.common.constants.Constants;
import com.demo.common.enums.IdcardResultEnum;
import com.demo.common.utils.DateUtil;
import com.demo.common.utils.DigitUtil;
import com.demo.common.utils.ResultUtil;
import com.demo.dao.po.SysAreaPO;
import com.demo.dao.repository.AreaRepository;
import demo.dubbo.common.Result;
import demo.dubbo.dto.response.AreaDTO;
import demo.dubbo.service.IdcardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service(version = "1.0",owner = "minksy")
@Component
public class IdcardServiceImpl implements IdcardService {

    @Autowired
    AreaRepository areaRepository;

    @Override
    public Result parseIdcard(String idcard) {
        log.info("传入参数为：{}", idcard);

        // 身份证校验
        if (!checkIdcard(idcard)) {
            return ResultUtil.error(IdcardResultEnum.ERROR_IDCARD);
        }

        // 查询该编号对应地区
        String area = getAreaInfo(idcard);
        if (StringUtils.isEmpty(area)) {
            return ResultUtil.error(IdcardResultEnum.ERROR_AREACODE);
        }

        // 解析生日
        String birthday = getBirthday(idcard);
        if (StringUtils.isEmpty(area)) {
            return ResultUtil.error(IdcardResultEnum.ERROR_BIRTHDAY);
        }

        String gender = getGender(idcard);

        AreaDTO areaDTO = getAreaDto(area, birthday, gender);
        return ResultUtil.success(areaDTO);
    }

    private AreaDTO getAreaDto(String area, String birthday, String gender) {
        AreaDTO dto = new AreaDTO();
        dto.setAreaName(area);
        dto.setBirthday(birthday);
        dto.setGender(gender);
        return dto;
    }

    @Override
    public String getAreaInfo(String areaCode) {
        // 省级地区编号
        String provinceCode = areaCode.substring(0, 2);
        Long addressCode = Long.valueOf(areaCode.substring(0, 6));
        String addrLevel;
        SysAreaPO area;

        switch (provinceCode) {
            // 此处筛选出直辖市的四个case
            case Constants.IdcardConstants.BEI_JING_CODE:
            case Constants.IdcardConstants.TIAN_JIN_CODE:
            case Constants.IdcardConstants.SHANG_HAI_CODE:
            case Constants.IdcardConstants.CHONG_QING_CODE:
                addrLevel = Constants.IdcardConstants.AREA_LEVEL_TWO;
                break;
            default:
                addrLevel = Constants.IdcardConstants.AREA_LEVEL_THREE;
                break;
        }

        if (Constants.IdcardConstants.AREA_LEVEL_TWO.equals(addrLevel)) {
            area = areaRepository.getLocationByCodeLv2(addressCode);
        } else {
            area = areaRepository.getLocationByCodeLv3(addressCode);
        }

        return getConcatAreaName(area);
    }

    // 拼接完整地区
    private String getConcatAreaName(SysAreaPO sysAreaPO) {

        if (null == sysAreaPO) {
            return null;
        }
        String province = sysAreaPO.getProvince();
        if (StringUtils.isEmpty(province)) {
            return sysAreaPO.getCity() + sysAreaPO.getDistrict();
        }
        String res =  province + sysAreaPO.getCity() + sysAreaPO.getDistrict();
        log.info("地址解析结果为:{}", res);

        return res;
    }

    @Override
    public String getBirthday(String dateNumSeries) {
        String birthday = dateNumSeries.substring(6, 14);
        try {
            Date date = new SimpleDateFormat(Constants.ComConstans.DATE_TYPE1).parse(birthday);
            Calendar calTime = Calendar.getInstance();
            calTime.setTime(date);
            int year = calTime.get(Calendar.YEAR);
            int month = calTime.get(Calendar.MONTH) + 1;
            int day = calTime.get(Calendar.DAY_OF_MONTH);
            String res =  "" + year + "年" + month + "月" + day + "日";
            log.info("生日解析结果为:{}", res);

            return res;
        } catch (Exception e) {
            log.error("解析出生日期失败：{}", e);
        }
        return null;
    }

    @Override
    public Boolean checkIdcard(String fullIdcardSeries) {
        if (StringUtils.isEmpty(fullIdcardSeries) ||
                fullIdcardSeries.length() != Constants.IdcardConstants.STANDARD_IDCARD_LENGTH) {
            log.error("身份证为空或者长度不为18位");
            return false;
        }

        String numPart = fullIdcardSeries.substring(0, 17);
        if (!DigitUtil.isNumberSeries(numPart)) {
            log.error("身份证数字部分有非法字符");
            return false;
        }

        // 判断出生日期是否合法
        String dateStr = fullIdcardSeries.substring(6, 14);
        if (!DateUtil.isValidGregDate(dateStr)) {
            log.error("身份证日期不合法");
            return false;
        }

        if (!DigitUtil.isValidIdCard(fullIdcardSeries)) {
            log.error("身份证有效性校验失败");
            return false;
        }

        return true;
    }

    @Override
    public String getGender(String idCardNumber) {
        String index = idCardNumber.substring(16, 17);
        int idInt = Integer.parseInt(index);
        String gender;
        if (idInt % 2 == 0) {
            gender = Constants.IdcardConstants.GENDER_FEMALE;
        } else {
            gender = Constants.IdcardConstants.GENDER_MALE;
        }
        return gender;
    }

    @Override
    public Result generateIdcard() {
        // 随机生成地区编号
        // 考虑到目前表中仅包含3749条编号记录,随机生成0-3749的编号作为id查询即可获得地区编号
        log.info("随机生成身份证号码接口被调用");
        boolean repeate = true;
        Random random = new Random();

        // 随机出来的地区编号可能是省级或者是市级; 有效的应该为区县级
        int id = 0;
        String codeStr = "";
        while (repeate) {
            // 地址表偏移量为 900000
            id = random.nextInt(3749) + 900001;
            log.info("随机生成的id为：{}", id);
            Long areaCode = areaRepository.getAreaCodeById(id);
            log.info("查询id得到areaCode为：{}", areaCode);

            if (checkAreaCode(areaCode)) {
                repeate = false;
                codeStr = String.valueOf(areaCode);
            }
        }

        // 随机生成年份,月份和日期
        // 取当前年份 减 0到60之间的随机数得到年份数据,然后随机生成月份和日期,最后判断完整年月日是否合法,若不合法重复这几步
        // 若生成日期不合法,则重新生成
        repeate = true;
        String dateStr = "";

        while (repeate) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);

            int yearDec = random.nextInt(40) + 10;
            int month = random.nextInt(12);
            int date = random.nextInt(31);

            year = year - yearDec;

            if (DateUtil.isValidDay(year, month, date)) {
                repeate = false;
                // 月份和日期统一用两位表示
                String monthStr = (month < 10) ? "0" + month : String.valueOf(month);
                String dayStr = (date < 10) ? "0" + date : String.valueOf(date);
                dateStr = String.valueOf(year) + monthStr + dayStr;
            }
        }

        // 随机生成倒数第四位 到 倒数第三位
        int birthNum = random.nextInt(1000);
        String birthNumStr = String.valueOf(birthNum);
        if (birthNum < 100) { //确保有三位
            birthNumStr = (birthNum < 10) ? "00" + birthNumStr : "0" + birthNumStr;
        }

        String numberPart = codeStr + dateStr + birthNumStr;
        // 遍历0 - X 让最后的身份证有效化
        int validValue = DigitUtil.getValidResult(numberPart);
        String validBit = (Constants.IdcardConstants.ROMAN_NUM_TEN == validValue) ? "X" : String.valueOf(validValue);

        // 返回有效身份证号码
        return ResultUtil.success(numberPart + validBit);
    }

    private Boolean checkAreaCode(Long areaCode) {
        String areaCodeStr = String.valueOf(areaCode);
        String partCut = "";
        for (int i = 0; i <= 4; i += 2) {
            // 有效的行政区域应该是形如"110102"的格式,不包含"00"
            partCut = areaCodeStr.substring(i, i + 2);
            if ("00".equals(partCut)) {
                return false;
            }
        }
        return true;
    }
}
