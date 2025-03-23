package org.qoerx.dict.demo;

import lombok.Data;
import lombok.ToString;
import org.qoerx.dict.annotation.Dict;

/**
 * @Author: wangshuo
 * @Data: 2025/3/21 11:22
 */
@Data
@ToString
public class dto
{
    @Dict(code = "name")
    private String name;

    @Dict(code = "sex")
    private String age;
}
