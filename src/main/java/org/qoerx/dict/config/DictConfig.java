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
     * Map集合对应key值
     * */
    private String mapKey = DictConstant.DICT_MAP_KEY;

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
}
