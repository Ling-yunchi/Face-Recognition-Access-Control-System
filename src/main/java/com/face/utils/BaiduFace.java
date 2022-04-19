package com.face.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.face.model.BaseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

/**
 * @author wangrong
 * @date 2022/4/18 17:09
 */
@Component
public class BaiduFace {
    private final WebClient webClient = WebClient
            .builder()
            .baseUrl("https://aip.baidubce.com/rest/2.0/face/v3")
            .defaultHeader("Content-Type", "application/json")
            .build();
    @Value("${baidu.clientId}")
    private String clientId;
    @Value("${baidu.clientSecret}")
    private String clientSecret;
    @Value("${baidu.groupId}")
    private String groupId;
    private String accessToken = "";

    public BaiduFace() {
        accessToken = getAuth();
    }

    private String getAuth() {
        var wc = WebClient.create("https://aip.baidubce.com/oauth/2.0/token");
        String res = wc.get().uri("?grant_type=client_credentials&client_id={clientId}&client_secret={clientSecret}", clientId, clientSecret)
                .retrieve()
                .bodyToMono(String.class).block();
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.getString("access_token");
    }

    public BaseResult<String> registerFace(String userId, String imageUrl) {
        JSONObject body = new JSONObject();
        body.put("image", imageUrl);
        body.put("image_type", "URL");
        body.put("group_id", groupId);
        body.put("user_id", userId);
        // 附加到user_id下，若user_id不存在则会报错
        body.put("action_type", "APPEND");

        String result = webClient.post().uri("/faceset/user/add?access_token={accessToken}", accessToken)
                .body(body.toJSONString(), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject jsonObject = JSONObject.parseObject(result);
        var res = new BaseResult<String>();
        if (jsonObject.get(Const.Baidu.ERROR_CODE) != null) {
            res.construct(false, jsonObject.getString(Const.Baidu.ERROR_MSG), "");
        } else {
            res.construct(true, jsonObject.getString("face_token"), "");
        }
        return res;
    }

    public BaseResult<String> updateFace(String userId, String imageUrl) {
        JSONObject body = new JSONObject();
        body.put("image", imageUrl);
        body.put("image_type", "URL");
        body.put("group_id", groupId);
        body.put("user_id", userId);
        // 会使用新图替换库中该user_id下所有图片, 若user_id不存在则会报错
        body.put("action_type", "UPDATE");

        String result = webClient.post().uri("/faceset/face/update?access_token={accessToken}", accessToken)
                .body(body.toJSONString(), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject jsonObject = JSONObject.parseObject(result);
        var res = new BaseResult<String>();
        if (jsonObject.get(Const.Baidu.ERROR_CODE) != null) {
            res.construct(false, jsonObject.getString(Const.Baidu.ERROR_MSG), "");
        } else {
            res.construct(true, jsonObject.getString("face_token"), "");
        }
        return res;
    }

    public BaseResult<String> deleteFace(String userId, String faceToken) {
        JSONObject body = new JSONObject();
        body.put("log_id", System.currentTimeMillis());
        body.put("user_id", userId);
        body.put("group_id", groupId);
        body.put("face_token", faceToken);

        String result = webClient.post().uri("/faceset/face/delete?access_token={accessToken}", accessToken)
                .body(body.toJSONString(), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject jsonObject = JSONObject.parseObject(result);
        var res = new BaseResult<String>();
        if (jsonObject.get(Const.Baidu.ERROR_CODE) != null) {
            res.construct(false, jsonObject.getString(Const.Baidu.ERROR_MSG), "");
        } else {
            res.construct(true, "", "");
        }
        return res;
    }

    public boolean authenticationFace(String imageUrl,String userId) {
        JSONObject body = new JSONObject();
        body.put("image", imageUrl);
        body.put("image_type", "URL");
        body.put("group_id_list", groupId);
        body.put("user_id", userId);


        String result = webClient.post().uri("/search?access_token={accessToken}", accessToken)
                .body(body.toJSONString(), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject jsonObject = JSONObject.parseObject(result);

        // TODO: 2022/4/19 修改返回结果
        if (jsonObject.get(Const.Baidu.ERROR_CODE) == null) {
            JSONArray jsonArray = jsonObject.getJSONArray("user_list");
            if (jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    // 如果识别到的人脸和指定的人脸相似度大于80，则返回true
                    if (jsonObject1.getString("user_id").equals(userId)
                            && jsonObject1.getDouble("score") > 80) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
