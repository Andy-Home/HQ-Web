package com.andy.dao.net;

import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.Map;

public class NetManager {

    private NetManager() {
        init();
    }

    static class SingleHolder {
        final static NetManager mInstance = new NetManager();
    }

    public static NetManager getInstance() {
        return SingleHolder.mInstance;
    }

    private OkHttpClient mClient;

    private void init() {
        mClient = new OkHttpClient();
    }

    public void requestQQUserInfo(String openId, String accessToken, BaseListener<Map<String, Object>> listener) {
        UpdateUserInfo updateUserInfo = new UpdateUserInfo();

        updateUserInfo.setChannel("QQ");

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("openId", openId);

        updateUserInfo.setParameter(map);
        updateUserInfo.run(mClient, listener);
    }
}
