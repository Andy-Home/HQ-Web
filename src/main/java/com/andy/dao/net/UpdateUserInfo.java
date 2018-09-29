package com.andy.dao.net;

import com.andy.Constant;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class UpdateUserInfo {
    private Logger mLog = Logger.getLogger(UpdateUserInfo.class);

    private String mChannel;

    public void setChannel(String channel) {
        mChannel = channel;
    }

    private Map<String, Object> mParameter;

    public void setParameter(Map<String, Object> map) {
        mParameter = map;
    }

    public <T extends BaseListener<Map<String, Object>>> void run(OkHttpClient mClient, final T listener) {
        String url;
        if (mChannel.equals("QQ")) {
            url = "https://graph.qq.com/user/get_user_info?"
                    + "access_token=" + mParameter.get("accessToken")
                    + "&oauth_consumer_key=" + Constant.QQ_APP_ID
                    + "&openid=" + mParameter.get("openId");

        } else {
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mLog.error("updateUserInfo", e.getCause());
                        listener.onError(e.toString());
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        JSONObject jsonObject = JSONObject.fromObject(response.body().string());

                        if (mChannel.equals("QQ")) {
                            int code = jsonObject.getInt("ret");

                            if (code == 0) {

                                Map<String, Object> map = new HashMap<>();

                                map.put("nickname", jsonObject.getString("nickname"));
                                map.put("headUrl", jsonObject.getString("figureurl_qq_1"));

                                String sex = jsonObject.getString("gender");
                                if (sex.equals("男")) {
                                    map.put("sex", 1);
                                } else {
                                    map.put("sex", 2);
                                }

                                listener.onSuccess(map);
                            } else {
                                listener.onError("QQ用户数据更新，返回code：" + code);
                            }
                        }

                        call.cancel();
                    }
                });
    }
}
