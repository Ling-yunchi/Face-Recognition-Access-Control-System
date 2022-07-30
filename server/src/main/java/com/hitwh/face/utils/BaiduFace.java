package com.hitwh.face.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.baidu.aip.face.AipFace;

/**
 * @author wangrong
 * @date 2022/4/18 17:09
 */
@Component
public class BaiduFace {
    private static AipFace client = null;
    @Value("${baidu.clientId}")
    private static String clientId;
    @Value("${baidu.clientSecret}")
    private static String clientSecret;
    @Value("${baidu.secretKey}")
    private static String secretKey;

    public static AipFace getClient() {
        if (client == null) {
            synchronized (BaiduFace.class) {
                client = new AipFace(clientId, clientSecret, secretKey);
                client.setConnectionTimeoutInMillis(2000);
                client.setSocketTimeoutInMillis(60000);
            }
        }
        return client;
    }
}
