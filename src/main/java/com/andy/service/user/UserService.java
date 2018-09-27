package com.andy.service.user;

import com.andy.dao.db.UserInfoDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserInfoDao mUserInfoDao;

    private int QQRegister(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        String accessToken = jsonObject.getString("accessToken");

        return mUserInfoDao.registerQQ(openId, accessToken);
    }

    private int WeiXinRegister(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        String accessToken = jsonObject.getString("accessToken");
        String refreshToken = jsonObject.getString("refreshToken");

        return mUserInfoDao.registerWeiXin(openId, accessToken, refreshToken);
    }

    public int login(JSONObject jsonObject) {
        String channel = jsonObject.getString("channel");

        Integer id = 0;
        if (channel.equals("QQ")) {

            String openId = jsonObject.getString("openId");
            id = mUserInfoDao.queryIdByQQOpenId(openId);

            if (id == null) {
                id = QQRegister(jsonObject);
            }

        } else if (channel.equals("WeiXin")) {

            String openId = jsonObject.getString("openId");
            id = mUserInfoDao.queryIdByWeiXinOpenId(openId);

            if (id == null) {
                id = WeiXinRegister(jsonObject);
            }
        }
        return id;
    }
}
