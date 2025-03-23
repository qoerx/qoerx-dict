package org.qoerx.dict.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 字典配置
 * @Author: wangshuo
 * @Data: 2025/3/21 14:55
 */
@Configuration
@ConfigurationProperties(prefix = "dict")
public class DictConfig {
}
