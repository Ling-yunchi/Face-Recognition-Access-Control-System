package com.face.utils;

import org.slf4j.LoggerFactory;

/**
 * @author wangrong
 * @date 2022/4/15 11:47
 */
public class Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Throwable e) {
        logger.error(msg, e);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }
}
