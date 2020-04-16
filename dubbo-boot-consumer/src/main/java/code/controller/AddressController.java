package code.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import demo.dubbo.common.Result;
import demo.dubbo.dto.request.AddressDto;
import demo.dubbo.dto.request.AddressParam;
import demo.dubbo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("address")
public class AddressController {

    // (1)增加缓存, 在CacheFilter 中，Dubbo会更具URL + methodName + 传参 去判断会否有缓存,
    //    若不存在缓存则直接走后续调用,存在则以传参作为key 去缓存中取数据
    // (2)缓存失效策略一般选择 LRU
    // (3)这里选择的是对provider的某个service进行缓存,还可以对服务端某个方法进行缓存如：
    // <dubbo:reference interface="com.foo.BarService">
    //    <dubbo:method name="findBar" cache="lru" />
    // </dubbo:reference>
    @Reference(version = "1.3",validation = "true")
    private AddressService addressService;

    @PostMapping("parse")
    public Result parseAddress(@RequestBody AddressParam param){

        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(param, dto);

        log.info("AddressController---parseAddress---入参：{}",dto);
        Result result = addressService.parseAddress(dto);
        return result;
    }
}

