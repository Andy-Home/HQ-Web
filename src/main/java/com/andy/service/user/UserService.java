package com.andy.service.user;

import com.andy.dao.db.UserInfoDao;
import com.andy.dao.entity.User;
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

        User user = new User();
        user.setQqOpenId(openId);
        user.setQqAccessToken(accessToken);

        mUserInfoDao.registerQQ(user);
        return user.getId();
    }

    private int WeiXinRegister(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        String accessToken = jsonObject.getString("accessToken");
        String refreshToken = jsonObject.getString("refreshToken");

        User user = new User();
        user.setWeiXinOpenId(openId);
        user.setWeiXinAccessToken(accessToken);
        user.setWeiXinRefreshToken(refreshToken);

        mUserInfoDao.registerWeiXin(user);
        return user.getId();
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
