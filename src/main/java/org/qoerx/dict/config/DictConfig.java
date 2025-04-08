package org.qoerx.dict.config;

import org.qoerx.dict.constant.DictConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 字典配置
 * @Author: wangshuo
 * @Data: 2025/3/21 20:55
 */
@Configuration
@ConfigurationProperties(prefix = DictConstant.DICT_CONFIG_NAME)
public class DictConfig {
    /**
     * 字典值后缀
     * */
    private String suffix = DictConstant.DICT_SUFFIX;

    /**
     * 返回结果Map集合对应默认key值
     * 适用于统一返回结果集的程序使用
     * */
    private String mapKey = DictConstant.DICT_MAP_KEY;

    /**
     * 最大递归深度
     * 可字典翻译的实体嵌套的最大深度
     * */
    private Integer depth = DictConstant.DICT_DEPTH;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
