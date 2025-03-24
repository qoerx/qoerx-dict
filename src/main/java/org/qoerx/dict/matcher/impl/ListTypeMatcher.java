package org.qoerx.dict.matcher.impl;

import org.qoerx.dict.matcher.ITypeMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * list类型匹配策略
 * @Author: wangshuo
 * @Data: 2025/3/24 20:10
 */
@Service
public class ListTypeMatcher implements ITypeMatcher {

    @Override
    public boolean matches(Object obj) {
        return List.class.isAssignableFrom(obj.getClass());
    }
}
