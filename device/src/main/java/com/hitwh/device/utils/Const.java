package com.hitwh.device.utils;

import java.nio.file.Paths;

/**
 * @author wangrong
 * @date 2022/7/30 21:41
 */
public class Const {
    public static final String RESOURCES_PATH;
    static {
        // Get relative path
        RESOURCES_PATH = Paths.get("").toAbsolutePath() + "\\device\\src\\main\\resources\\";
    }
}
