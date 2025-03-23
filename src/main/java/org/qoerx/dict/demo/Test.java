package org.qoerx.dict.demo;


//import lombok.Data;
import org.qoerx.dict.annotation.DictTransform;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangshuo
 * @Data: 2025/3/18 16:12
 */
@RestController
public class Test {

    @RequestMapping("/dto")
    @DictTransform
    public List<dto> getDtoList() {
        return this.getDto();
    }

    public List<dto> getDto(){
        return new ArrayList<dto>(){{
            dto dto = new dto();
            dto.setAge("1");
            dto.setName("wangshuo");
            add(dto);
        }};
    }
}

