/**
 * Copyright 2020-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.data.monitor.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author binghe
 * @version 1.0.0
 * @description 监听类型枚举
 */
public enum ListenerTypeEnum {

    /**
     * 定时
     */
    TIMING("timing"),
    /**
     * 日志
     */
    LOG("log");

    private String type;

    ListenerTypeEnum(String type) {
        this.type = type;
    }

    public static boolean isTiming(String type) {
        return StringUtils.equals(TIMING.getType(), type);
    }

    public static boolean isLog(String type) {
        return StringUtils.equals(LOG.getType(), type);
    }

    public String getType() {
        return type;
    }
}
