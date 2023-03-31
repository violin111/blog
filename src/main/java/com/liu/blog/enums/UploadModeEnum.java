package com.liu.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadModeEnum {
    /**
     *OSS
     */
    OSS("oss","ossUploadStrategyImpl"),
    /**
     * COS
     */
    COS("cos","cosUploadStrategyImpl"),
    /**
     * 本地
     */
    LOCAL("local","localUploadStrategyImpl");

    /**
     *模式
     */
    private final String mode;
    /**
     * 策略
     */
    private final String strategy;

    /**
     *获取策略
     * @param mode
     * @return
     */
    public static String getStrategy(String mode){
        for (UploadModeEnum value : UploadModeEnum.values()) {
            if(value.getMode().equals(mode)){
                return value.getStrategy();
            }
        }
        return null;
    }


}
